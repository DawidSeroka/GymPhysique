package com.myproject.gymphysique.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myproject.gymphysique.core.database.DatabaseConstants.TABLE_MEASUREMENT
import com.myproject.gymphysique.core.model.MeasurementType

@Entity(tableName = TABLE_MEASUREMENT)
data class MeasurementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val measurementResult: Double,
    val timestamp: Long = 0,
    val measurementType: MeasurementType
)
