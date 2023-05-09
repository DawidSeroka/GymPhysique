package com.myproject.gymphysique.core.decoder.responseDecode

import com.myproject.gymphysique.core.common.Result
import com.myproject.gymphysique.core.common.signedBytesToInt
import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.decoder.flag.FlagTypeCheck
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@Suppress("MagicNumber")
class BodyCompositionResponseDecode @Inject constructor(
    private val flagTypeCheck: FlagTypeCheck.BodyCompositionFlagTypeCheck,
    private val decodeImpedance: DecodeImpedance
) {
    private var lbmCoefficient: Double? = null

    fun decodeBodyComposition(
        byteArray: ByteArray,
        sex: String,
        height: Int,
        age: Int
    ): Result<ResponseData> {
        val flags = signedBytesToInt(byteArray[0], byteArray[1])
        val sizeOfList = byteArray.size
        val metricUnit = !flagTypeCheck.checkBodyCompositionFlagUnit(flags)

        val weight = decodeWeight(flags, byteArray, sizeOfList, metricUnit)
        val date = getCurrentDate()
        val impedancePresent = flagTypeCheck.checkBodyCompositionFlagImpedance(flags)
        return if (impedancePresent) {
            var impedance: Int? = null
            impedance = if (flagTypeCheck.checkBodyCompositionFlagHeight(flags)) {
                signedBytesToInt(
                    byteArray[sizeOfList - 6],
                    byteArray[sizeOfList - 5]
                )
            } else {
                signedBytesToInt(
                    byteArray[sizeOfList - 4],
                    byteArray[sizeOfList - 3]
                )
            }
            weight?.let {
                lbmCoefficient = decodeImpedance.getLBMCoefficient(it, height, impedance, age)
                val fatPercentage = decodeFatPercentage(sex, age, weight, height)
                val bmr = decodeBMR(sex, it, height, age)
                val boneMass = decodeBoneMass(sex)
                val muscleMass = decodeMuscleMass(sex, it, fatPercentage, boneMass)
                val musclePercentage = ((muscleMass * 100) / it).roundTo2Places()
                val waterPercentage = decodeWaterPercentage(fatPercentage)
                val visceralFat = decodeVisceralFat(sex, it, height, age)
                val idealWeight = decodeIdealWeight(sex = sex, height = height)
                val bmi = decodeBMI(height, it)

                Result.success(
                    ResponseData.BodyCompositionResponseData(
                        weight = it,
                        date = date,
                        bodyFatPercentage = fatPercentage,
                        basalMetabolism = bmr,
                        musclePercentage = musclePercentage,
                        muscleMass = muscleMass,
                        visceralFat = visceralFat,
                        bodyWaterPercentage = waterPercentage,
                        boneMass = boneMass,
                        idealWeight = idealWeight,
                        bmi = bmi,
                        height = height
                    )
                )
            } ?: Result.error(Exception("Error occurred"))
        } else {
            Result.loading(
                ResponseData.BodyCompositionResponseData(
                    weight = weight,
                    date = date
                )
            )
        }
    }

    private fun decodeWeight(
        flags: Int,
        byteArray: ByteArray,
        sizeOfList: Int,
        metricUnit: Boolean
    ): Double? {
        return if (flagTypeCheck.checkBodyCompositionFlagWeightPresent(flags)) {
            val rawWeight = signedBytesToInt(
                byteArray[sizeOfList - 2],
                byteArray[sizeOfList - 1]
            )
            return if (metricUnit) {
                (rawWeight / 200.0).roundTo2Places()
            } else {
                (rawWeight * 0.45359237).roundTo2Places()
            }
        } else {
            null
        }
    }

    private fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.now().format(formatter)
    }

    @Suppress("ComplexMethod")
    private fun decodeFatPercentage(sex: String, age: Int, weight: Double, height: Int): Double {
        // Set a constant to remove from LBM
        val const: Double = when {
            sex == "female" && age <= 49 -> 9.25
            sex == "female" && age > 49 -> 7.25
            else -> 0.8
        }

        // Calculate body fat percentage
        val lbm = lbmCoefficient!!

        val coefficient: Double = when {
            sex == "male" && weight < 61 -> 0.98
            sex == "female" && weight > 60 -> {
                var coeff = 0.96
                if (height > 160) {
                    coeff *= 1.03
                }
                coeff
            }
            sex == "female" && weight < 50 -> {
                var coeff = 1.02
                if (height > 160) {
                    coeff *= 1.03
                }
                coeff
            }
            else -> 1.0
        }

        var fatPercentage = (1.0 - (((lbm - const) * coefficient) / weight)) * 100

        // Capping body fat percentage
        if (fatPercentage > 63) {
            fatPercentage = 75.0
        }
        return decodeImpedance.checkValueOverflow(fatPercentage, 5.0, 75.0).roundTo2Places()
    }

    private fun decodeWaterPercentage(fatPercentage: Double): Double {
        val waterPercentage = (100 - fatPercentage) * 0.7

        val coefficient = if (waterPercentage <= 50) {
            1.02
        } else {
            0.98
        }

        // Capping water percentage
        var cappedWaterPercentage = waterPercentage * coefficient
        if (cappedWaterPercentage >= 65) {
            cappedWaterPercentage = 75.0
        }
        return decodeImpedance.checkValueOverflow(cappedWaterPercentage, 35.0, 75.0)
            .roundTo2Places()
    }

    // Get bone mass
    private fun decodeBoneMass(sex: String): Double {
        val base = if (sex == "female") {
            0.245691014
        } else {
            0.18016894
        }

        var boneMass = (base - (lbmCoefficient!! * 0.05158)) * -1

        if (boneMass > 2.2) {
            boneMass += 0.1
        } else {
            boneMass -= 0.1
        }

        // Capping boneMass
        when {
            sex == "female" && boneMass > 5.1 -> boneMass = 8.0
            sex == "male" && boneMass > 5.2 -> boneMass = 8.0
        }

        return decodeImpedance.checkValueOverflow(boneMass, 0.5, 8.0).roundTo2Places()
    }

    // Get muscle mass
    private fun decodeMuscleMass(
        sex: String,
        weight: Double,
        fatPercentage: Double,
        boneMass: Double
    ): Double {
        var muscleMass = weight - ((fatPercentage * 0.01) * weight) - boneMass

        // Capping muscle mass
        if (sex == "female" && muscleMass >= 84) {
            muscleMass = 120.0
        } else if (sex == "male" && muscleMass >= 93.5) {
            muscleMass = 120.0
        }

        return decodeImpedance.checkValueOverflow(muscleMass, 10.0, 120.0).roundTo2Places()
    }

    // Get Visceral Fat
    private fun decodeVisceralFat(sex: String, weight: Double, height: Int, age: Int): Double {
        val vfal: Double
        if (sex == "female") {
            if (weight > (13 - (height * 0.5)) * -1) {
                val subsubcalc = ((height * 1.45) + (height * 0.1158) * height) - 120
                val subcalc = weight * 500 / subsubcalc
                vfal = (subcalc - 6) + (age * 0.07)
            } else {
                val subcalc = 0.691 + (height * -0.0024) + (height * -0.0024)
                vfal = (((height * 0.027) - (subcalc * weight)) * -1) + (age * 0.07) - age
            }
        } else {
            if (height < weight * 1.6) {
                val subcalc = ((height * 0.4) - (height * (height * 0.0826))) * -1
                vfal = ((weight * 305) / (subcalc + 48)) - 2.9 + (age * 0.15)
            } else {
                val subcalc = 0.765 + height * -0.0015
                vfal = (((height * 0.143) - (weight * subcalc)) * -1) + (age * 0.15) - 5.0
            }
        }

        return decodeImpedance.checkValueOverflow(vfal, 1.0, 50.0).roundTo2Places()
    }

    // Get BMI
    private fun decodeBMI(height: Int, weight: Double): Double {
        val heightInMeter = height / 100.0 // converting height to meters
        val bmi = weight / (heightInMeter * heightInMeter)
        return decodeImpedance.checkValueOverflow(bmi, 10.0, 90.0).roundTo2Places()
    }

    // Get ideal weight
    private fun decodeIdealWeight(orig: Boolean = true, sex: String, height: Int): Double {
        // Using mi fit algorithm (or holtek's one)
        if (orig && sex == "female") {
            return (height - 70) * 0.6
        } else if (orig && sex == "male") {
            return (height - 80) * 0.7
        } else {
            return decodeImpedance.checkValueOverflow((22 * height) * height / 10000.0, 5.5, 198.0)
                .roundTo2Places()
        }
    }

    private fun decodeBMR(sex: String, weight: Double, height: Int, age: Int): Double {
        var bmr: Double
        if (sex == "female") {
            bmr = 864.6 + weight * 10.2036
            bmr -= height * 0.39336
            bmr -= age * 6.204
        } else {
            bmr = 877.8 + weight * 14.916
            bmr -= height * 0.726
            bmr -= age * 8.976
        }

        // Capping
        if (sex == "female" && bmr > 2996 || sex == "male" && bmr > 2322) {
            return decodeImpedance.checkValueOverflow(5000.0, 500.0, 10000.0)
        }

        return decodeImpedance.checkValueOverflow(bmr, 500.0, 10000.0).roundTo2Places()
    }

    fun Double.roundTo2Places(): Double {
        return (this * 100).toInt() / 100.0
    }
}
