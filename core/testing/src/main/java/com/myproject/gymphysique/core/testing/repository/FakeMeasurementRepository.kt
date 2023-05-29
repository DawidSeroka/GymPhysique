package com.myproject.gymphysique.core.testing.repository

import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map

class FakeMeasurementRepository : MeasurementRepository {

    private val measurementsFlow: MutableSharedFlow<List<Measurement>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun observeMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): Flow<List<Measurement>> {
        return measurementsFlow.map { currentList ->
            currentList.filter { measurement ->
                measurement.date.contains(dateParam) && measurement.measurementType == measurementType
            }
        }.filterNot { it.isEmpty() }
    }

    override suspend fun getMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): List<Measurement> {
        return measurementsFlow.replayCache.firstOrNull()?.let { currentList ->
            return currentList
                .filter { it.date.contains(dateParam) && it.measurementType == measurementType }
        } ?: emptyList()
    }

    override suspend fun saveMeasurement(measurement: Measurement): Long {
        val measurementList = measurementsFlow.replayCache.firstOrNull() ?: emptyList()
        val maxId = measurementList.maxOfOrNull { it.id }
        val newId = maxId?.let { it + 1 } ?: 0
        val newList = measurementList + measurement.copy(id = newId)
        measurementsFlow.tryEmit(newList)
        return newId.toLong()
    }

    suspend fun initData() {
        mockMeasurements.forEach { saveMeasurement(it) }
    }
}

private val mockMeasurements = listOf(
    Measurement(0, 14.0, date = "2023-05-24", measurementType = MeasurementType.WEIGHT),
    Measurement(1, 15.0, date = "2023-05-23", measurementType = MeasurementType.WEIGHT),
    Measurement(2, 16.0, date = "2023-04-24", measurementType = MeasurementType.WEIGHT),
    Measurement(3, 17.0, date = "2023-06-24", measurementType = MeasurementType.BMI)
)
