package com.myproject.gymphysqiue.core.domain.decode

import com.myproject.gymphysique.core.common.Result
import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import timber.log.Timber
import javax.inject.Inject

@Suppress("LongMethod", "MagicNumber")
class DecodeResponseUseCase @Inject constructor() : (Result<ResponseData>) -> List<Measurement> {
    override fun invoke(result: Result<ResponseData>): List<Measurement> {
        return if (result.isLoading()) {
            val loadingMeasurement =
                result.value() as ResponseData.BodyCompositionResponseData

            listOf(
                Measurement(
                    measurementResult = loadingMeasurement.weight ?: 0.0,
                    date = loadingMeasurement.date,
                    measurementType = MeasurementType.WEIGHT
                )
            )
        } else if (result.isSuccess()) {
            val measurementResponse =
                result.value() as ResponseData.BodyCompositionResponseData
            val measurementWeight = Measurement(
                measurementResult = measurementResponse.weight ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.WEIGHT
            )
            val measurementFatPercentage = Measurement(
                measurementResult = measurementResponse.bodyFatPercentage
                    ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.BODY_FAT
            )
            val measurementBmr = Measurement(
                measurementResult = measurementResponse.basalMetabolism
                    ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.BASAL_METABOLISM
            )
            val measurementBoneMass = Measurement(
                measurementResult = measurementResponse.boneMass ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.BONE_MASS
            )
            val measurementMuscleMass = Measurement(
                measurementResult = measurementResponse.muscleMass ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.MUSCLE_MASS
            )
            val measurementMusclePercentage = Measurement(
                measurementResult = measurementResponse.musclePercentage
                    ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.MUSCLE_PERCENTAGE
            )
            val measurementWaterPercentage = Measurement(
                measurementResult = measurementResponse.bodyWaterPercentage
                    ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.BODY_WATER_MASS
            )
            val measurementVisceralFat = Measurement(
                measurementResult = measurementResponse.visceralFat ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.VISCERAL_FAT
            )
            val measurementIdealWeight = Measurement(
                measurementResult = measurementResponse.idealWeight ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.IDEAL_WEIGHT
            )
            val measurementBmi = Measurement(
                measurementResult = measurementResponse.bmi ?: 0.0,
                date = measurementResponse.date,
                measurementType = MeasurementType.BMI
            )

            listOf(
                measurementWeight,
                measurementBmi,
                measurementBmr,
                measurementBoneMass,
                measurementMuscleMass,
                measurementFatPercentage,
                measurementVisceralFat,
                measurementIdealWeight,
                measurementWaterPercentage,
                measurementMusclePercentage
            )
        } else {
            val measurementResponse =
                result.value() as ResponseData.BodyCompositionResponseData
            Timber.d("Result3=$measurementResponse")

            emptyList()
        }
    }
}
