package com.myproject.gymphysique.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myproject.gymphysique.core.database.dao.MeasurementDao
import com.myproject.gymphysique.core.database.model.MeasurementEntity

@Database(entities = [MeasurementEntity::class], version = 1, exportSchema = false)
abstract class GpDatabase : RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
}
