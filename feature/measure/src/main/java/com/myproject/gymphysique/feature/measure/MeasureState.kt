package com.myproject.gymphysique.feature.measure

import com.juul.kable.Advertisement

internal data class MeasureState(
    val advertisementStatus: AdvertisementStatus = AdvertisementStatus.STOPPED,
    val advertisements: List<Advertisement> = mutableListOf()
)

internal enum class AdvertisementStatus(val text: String){
    ADVERTISING("Advertising"),
    CONNECTING("Connecting"),
    CONNECTED("Connected"),
    STOPPED("Stoppped"),

}