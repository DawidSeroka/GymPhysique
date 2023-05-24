package com.myproject.gymphysqiue.core.domain.app

import com.myproject.gymphysique.core.common.getCurrentDate
import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.MeasurementType
import javax.inject.Inject

class ObserveIfDailyMeasurementExistUseCase @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : suspend () -> Boolean {
    override suspend fun invoke(): Boolean {
        val currentDate = getCurrentDate()
        return measurementRepository
            .getMeasurements(currentDate, MeasurementType.WEIGHT)
            .isNotEmpty()
    }
}
