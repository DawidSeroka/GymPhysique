package com.myproject.gymphysique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myproject.gymphysique.core.database.DatabaseConstants.TABLE_MEASUREMENT
import com.myproject.gymphysique.core.database.model.MeasurementEntity
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM $TABLE_MEASUREMENT WHERE strftime('%Y-%m', date) = :dateParam AND measurementType =:measurementType")
    fun observeMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): Flow<List<MeasurementEntity>>

    @Query("SELECT * FROM $TABLE_MEASUREMENT WHERE strftime('%Y-%m', date) = :dateParam AND measurementType =:measurementType")
    suspend fun getMeasurements(
        dateParam: String,
        measurementType: MeasurementType
    ): List<MeasurementEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurementEntity: MeasurementEntity): Long
}
