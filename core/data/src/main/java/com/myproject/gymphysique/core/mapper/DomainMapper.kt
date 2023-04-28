package com.myproject.gymphysique.core.mapper

import com.myproject.gymphysique.core.database.model.MeasurementEntity
import com.myproject.gymphysique.core.model.Measurement

internal fun MeasurementEntity.toDomain(): Measurement =
    Measurement(
        id = id,
        measurementResult = measurementResult,
        date = date,
        measurementType = measurementType
    )