package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.database.dao.MeasurementDao
import com.myproject.gymphysique.core.mapper.toDomain
import com.myproject.gymphysique.core.mapper.toEntity
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasurementRepositoryImpl @Inject constructor(
    private val measurementDao: MeasurementDao
) : MeasurementRepository {

    override fun observeMeasurements(dateParam: String, measurementType: MeasurementType): Flow<List<Measurement>> {
        return measurementDao.observeMeasurements(dateParam, measurementType).map { measurementList ->
            measurementList.map { it.toDomain() }
        }.filterNot { it.isEmpty() }
    }

    override suspend fun getMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): List<Measurement> {
        return measurementDao.getMeasurements(dateParam, measurementType).map { measurement ->
            measurement.toDomain()
        }
    }

    override suspend fun saveMeasurement(measurement: Measurement): Long {
        return measurementDao.insertMeasurement(measurement.toEntity())
    }
}
