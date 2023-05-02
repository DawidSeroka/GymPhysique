package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.model.Measurement
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    fun getMeasurements(): Flow<List<Measurement>>

    fun getMeasurementsWithDate(dateParam: String): Flow<List<Measurement>>

    suspend fun saveMeasurement(measurement: Measurement)
}
