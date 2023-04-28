package com.myproject.gymphysique.feature.charts

import com.myproject.gymphysique.core.model.Measurement

data class ChartsState(
    val weightMeasurements: List<Measurement> = mutableListOf(),
    val bmiMeasurements: List<Measurement> = mutableListOf(),
    val bodyFatMeasurements: List<Measurement> = mutableListOf(),
    val musclePercentageMeasurements: List<Measurement> = mutableListOf(),
    val muscleMassMeasurements: List<Measurement> = mutableListOf(),
    val fatFreeMassMeasurements: List<Measurement> = mutableListOf(),
    val softLeanMassMeasurements: List<Measurement> = mutableListOf(),
    val bodyWaterMassMeasurements: List<Measurement> = mutableListOf(),
    val boneMassMeasurements: List<Measurement> = mutableListOf(),
    val visceralFatMeasurements: List<Measurement> = mutableListOf(),
    val idealWeightMeasurements: List<Measurement> = mutableListOf(),
    val basalMetabolismMeasurements: List<Measurement> = mutableListOf(),

    val selectedDate: String? = null
)