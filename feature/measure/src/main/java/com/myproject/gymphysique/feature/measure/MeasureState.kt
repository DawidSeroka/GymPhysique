package com.myproject.gymphysique.feature.measure

import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.common.UiText
import com.myproject.gymphysique.core.model.ConnectionState
import com.myproject.gymphysique.core.model.Measurement

internal data class MeasureState(
    val scanTime: Int? = null,
    val advertisingStatus: AdvertisingStatus = AdvertisingStatus.STOPPED,
    val advertisements: List<AdvertisementWrapper> = mutableListOf(),
    val measurements: List<Measurement> = mutableListOf(),
    val measureState: Boolean = false,
    val saveMeasurementResult: SaveOperationResult? = null
)

internal data class AdvertisementWrapper(
    val connectionState: ConnectionState,
    val advertisement: Advertisement
)

internal enum class AdvertisingStatus(val text: String) {
    ADVERTISING("Advertising"),
    STOPPED("Stopped")
}

internal interface SaveOperationResult {
    data class Success(val message: UiText) : SaveOperationResult
    data class Error(val message: UiText) : SaveOperationResult
}
