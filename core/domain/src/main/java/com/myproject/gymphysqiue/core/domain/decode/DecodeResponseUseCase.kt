package com.myproject.gymphysqiue.core.domain.decode

import com.myproject.gymphysique.core.common.Result
import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import javax.inject.Inject

class DecodeResponseUseCase @Inject constructor() : (Result<ResponseData>) -> List<Measurement> {

    override fun invoke(result: Result<ResponseData>): List<Measurement> {
        return when {
            result.isLoading() -> listOf(
                createMeasurement(
                    result.value() as ResponseData.BodyCompositionResponseData,
                    MeasurementType.WEIGHT
                )
            )

            result.isSuccess() -> createMeasurementsList(result.value() as ResponseData.BodyCompositionResponseData)
            else -> emptyList() // Handle other cases like error or empty
        }
    }

    private fun createMeasurement(
        responseData: ResponseData.BodyCompositionResponseData,
        type: MeasurementType
    ) = Measurement(
        measurementResult = when (type) {
            MeasurementType.WEIGHT -> responseData.weight
            MeasurementType.BODY_FAT -> responseData.bodyFatPercentage
            MeasurementType.BASAL_METABOLISM -> responseData.basalMetabolism
            MeasurementType.BONE_MASS -> responseData.boneMass
            MeasurementType.MUSCLE_MASS -> responseData.muscleMass
            MeasurementType.MUSCLE_PERCENTAGE -> responseData.musclePercentage
            MeasurementType.BODY_WATER_MASS -> responseData.bodyWaterPercentage
            MeasurementType.VISCERAL_FAT -> responseData.visceralFat
            MeasurementType.IDEAL_WEIGHT -> responseData.idealWeight
            MeasurementType.BMI -> responseData.bmi
        } ?: 0.0,
        date = responseData.date,
        measurementType = type
    )

    private fun createMeasurementsList(responseData: ResponseData.BodyCompositionResponseData) =
        listOf(
            MeasurementType.WEIGHT,
            MeasurementType.BMI,
            MeasurementType.BASAL_METABOLISM,
            MeasurementType.BONE_MASS,
            MeasurementType.MUSCLE_MASS,
            MeasurementType.BODY_FAT,
            MeasurementType.VISCERAL_FAT,
            MeasurementType.IDEAL_WEIGHT,
            MeasurementType.BODY_WATER_MASS,
            MeasurementType.MUSCLE_PERCENTAGE
        ).map { type -> createMeasurement(responseData, type) }
}
