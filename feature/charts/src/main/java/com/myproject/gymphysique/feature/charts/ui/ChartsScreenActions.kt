package com.myproject.gymphysique.feature.charts.ui

import com.myproject.gymphysique.core.model.MeasurementType
import java.util.Date

internal data class ChartsScreenActions(
    val onMeasurementDropdownSelected: () -> Unit,
    val onMeasurementTypeSelected: (MeasurementType) -> Unit,
    val onDateDropdownSelected: () -> Unit,
    val onDateSelected: (Date) -> Unit
)
