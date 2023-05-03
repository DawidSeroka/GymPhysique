package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    fun observeMeasurements(dateParam: String, measurementType: MeasurementType): Flow<List<Measurement>>

    suspend fun getMeasurements(dateParam: String, measurementType: MeasurementType): List<Measurement>

    suspend fun saveMeasurement(measurement: Measurement)
}
