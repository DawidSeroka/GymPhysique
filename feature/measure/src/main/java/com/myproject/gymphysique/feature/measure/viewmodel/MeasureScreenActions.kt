package com.myproject.gymphysique.feature.measure.viewmodel

import com.juul.kable.Advertisement

internal data class MeasureScreenActions(
    val onSearchDevicesClick: () -> Unit,
    val onConnectDeviceClick: (Advertisement) -> Unit,
    val onSearchMeasurementsClick: () -> Unit,
    val onSaveMeasurementsClick: () -> Unit,
    val onStopMeasureClick: () -> Unit,
    val onDisconnectClick: () -> Unit
)