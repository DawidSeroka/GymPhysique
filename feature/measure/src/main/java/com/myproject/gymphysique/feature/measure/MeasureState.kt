package com.myproject.gymphysique.feature.measure

import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.model.Measurement

internal data class MeasureState(
    val scanTime: Int? = null,
    val advertisingStatus: AdvertisingStatus = AdvertisingStatus.STOPPED,
    val advertisements: List<Pair<PeripheralState, Advertisement>> = mutableListOf(),
    val measurements: List<Measurement> = mutableListOf(),
    val measureState: Boolean = false
)

internal enum class AdvertisingStatus(val text: String){
    ADVERTISING("Advertising"),
    STOPPED("Stopped"),
}

internal enum class PeripheralState{
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    DISCONNECTING
}