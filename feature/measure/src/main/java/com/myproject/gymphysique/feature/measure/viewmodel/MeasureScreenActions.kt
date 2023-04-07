package com.myproject.gymphysique.feature.measure.viewmodel

import com.juul.kable.Advertisement

internal data class MeasureScreenActions(
    val onSearchDevicesClick: () -> Unit,
    val onConnectDeviceClick: (Advertisement) -> Unit
)