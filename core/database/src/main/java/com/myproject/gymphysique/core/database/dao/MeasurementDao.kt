package com.myproject.gymphysique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myproject.gymphysique.core.database.DatabaseConstants.TABLE_MEASUREMENT
import com.myproject.gymphysique.core.database.model.MeasurementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Query("SELECT * FROM $TABLE_MEASUREMENT")
    fun getAllMeasurements(): Flow<List<MeasurementEntity>>

    @Query("SELECT * FROM $TABLE_MEASUREMENT WHERE strftime('%Y-%m', date) = :dateParam")
    fun getMeasurementsWithDate(dateParam: String): Flow<List<MeasurementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurementEntity: MeasurementEntity)
}
