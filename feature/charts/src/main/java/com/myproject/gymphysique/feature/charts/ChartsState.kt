package com.myproject.gymphysique.feature.charts

import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType

data class ChartsState(
    val measurements: List<Measurement> = mutableListOf(),

    val selectedDate: String? = null,
    val selectedMeasurementType: MeasurementType = MeasurementType.WEIGHT,
    val dropdownDateExpanded: Boolean = false,
    val dropdownMeasurementExpanded: Boolean = false
)
