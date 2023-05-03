package com.myproject.gymphysique.feature.charts.ui

import com.myproject.gymphysique.core.model.MeasurementType

internal data class ChartsScreenActions (
    val onMeasurementDropdownSelected: () -> Unit,
    val onMeasurementTypeSelected: (MeasurementType) -> Unit,
    val onDateDropdownSelected: () -> Unit,
    val onDateSelected: (String) -> Unit
)