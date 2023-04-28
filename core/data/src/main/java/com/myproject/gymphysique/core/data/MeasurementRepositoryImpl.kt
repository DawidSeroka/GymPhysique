package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.database.dao.MeasurementDao
import com.myproject.gymphysique.core.mapper.toDomain
import com.myproject.gymphysique.core.mapper.toEntity
import com.myproject.gymphysique.core.model.Measurement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasurementRepositoryImpl @Inject constructor(
    private val measurementDao: MeasurementDao
): MeasurementRepository{

    override fun getMeasurements(): Flow<List<Measurement>> {
        return measurementDao.getAllMeasurements().map { measurementList ->
            measurementList.map { it.toDomain() }
        }
    }

    override suspend fun saveMeasurement(measurement: Measurement) {
        measurementDao.insertMeasurement(measurement.toEntity())
    }
}
