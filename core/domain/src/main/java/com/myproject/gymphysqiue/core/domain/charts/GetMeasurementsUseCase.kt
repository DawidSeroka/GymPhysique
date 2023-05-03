package com.myproject.gymphysqiue.core.domain.charts

import com.myproject.gymphysique.core.common.getCurrentDate
import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import javax.inject.Inject

class GetMeasurementsUseCase @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : suspend (String, MeasurementType) -> List<Measurement> {
    override suspend fun invoke(
        dateParam: String,
        measurementType: MeasurementType
    ): List<Measurement> {
        return measurementRepository.getMeasurements(dateParam, measurementType)
    }
}