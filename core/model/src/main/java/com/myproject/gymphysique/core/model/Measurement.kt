package com.myproject.gymphysique.core.model

data class Measurement(
    val id: Int = 0,
    val measurementResult: Double,
    val date: String = "",
    val measurementType: MeasurementType
)
