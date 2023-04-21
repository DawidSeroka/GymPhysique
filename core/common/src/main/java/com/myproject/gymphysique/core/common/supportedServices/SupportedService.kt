package com.myproject.gymphysique.core.common.supportedServices

import com.myproject.gymphysique.core.common.characteristics.Characteristics

enum class SupportedService(
    val uuid: String,
    val fullName: String,
    val characteristics: Characteristics
) {
    BODY_COMPOSITION(
        uuid = "0000181b-0000-1000-8000-00805f9b34fb",
        fullName = "Body composition",
        characteristics = Characteristics.BODY_COMPOSITION_CHARACTERISTICS
    )
}