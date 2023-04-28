package com.myproject.gymphysique.core.mapper

import com.myproject.gymphysique.core.database.model.MeasurementEntity
import com.myproject.gymphysique.core.model.Measurement

internal fun Measurement.toEntity(): MeasurementEntity =
    MeasurementEntity(
        id = id,
        measurementResult = measurementResult,
        timestamp = timestamp,
        measurementType = measurementType
    )