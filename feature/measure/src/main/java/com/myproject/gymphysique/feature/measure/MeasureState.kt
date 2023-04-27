package com.myproject.gymphysique.feature.measure

import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.ConnectionState

internal data class MeasureState(
    val scanTime: Int? = null,
    val advertisingStatus: AdvertisingStatus = AdvertisingStatus.STOPPED,
    val advertisements: List<AdvertisementWrapper> = mutableListOf(),
    val measurements: List<Measurement> = mutableListOf(),
    val measureState: Boolean = false
)

internal data class AdvertisementWrapper(
    val connectionState: ConnectionState,
    val advertisement: Advertisement
)

internal enum class AdvertisingStatus(val text: String){
    ADVERTISING("Advertising"),
    STOPPED("Stopped"),
}
