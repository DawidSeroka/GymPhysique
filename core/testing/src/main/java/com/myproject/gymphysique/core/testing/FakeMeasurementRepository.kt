package com.myproject.gymphysique.core.testing

import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf

class FakeMeasurementRepository : MeasurementRepository {

    private val measurementsFlow: MutableSharedFlow<List<Measurement>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun observeMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): Flow<List<Measurement>> {
        return if (measurementsFlow.replayCache.isEmpty())
            flowOf(emptyList())
        else
            measurementsFlow
    }

    override suspend fun getMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): List<Measurement> {
        return measurementsFlow
            .replayCache.firstOrNull() ?: emptyList()
    }

    override suspend fun saveMeasurement(measurement: Measurement): Long {
        val measurementList = measurementsFlow.replayCache.firstOrNull() ?: emptyList()
        val maxId = measurementList.maxOfOrNull { it.id }
        val newId = maxId?.let { it + 1 } ?: 0
        val newMeasurement = measurement.copy(id = newId)
        val newList = measurementList + newMeasurement
        measurementsFlow.tryEmit(newList)
        return newId.toLong()
    }

}