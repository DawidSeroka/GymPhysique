package com.myproject.gymphysqiue.core.domain.measure

import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.Measurement
import javax.inject.Inject

class AddMeasurementUseCase @Inject constructor(
    private val repository: MeasurementRepository
): suspend (Measurement) -> Unit {
    override suspend fun invoke(measurement: Measurement) {
        repository.saveMeasurement(measurement)
    }
}