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

internal sealed class SaveOperationResult(val message: UiText) {
    data class Success(val data: UiText) : SaveOperationResult(message = data)
    data class Error(val errorMessage: UiText) : SaveOperationResult(message = errorMessage)
}
