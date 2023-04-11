package com.myproject.gymphysique.core.common.characteristics

enum class Characteristics(
    val fullName: String,
    val characteristics: BasicCharacteristics
) {
    BODY_COMPOSITION_CHARACTERISTICS(
        fullName = "Body composition characteristics",
        characteristics = BasicCharacteristics.BodyCompositionCharacteristics
    )
}

sealed class BasicCharacteristics(
    val indication: Characteristic?,
    val read: Characteristic?,
    val write: Characteristic?,
    val notify: Characteristic?
){
    object BodyCompositionCharacteristics: BasicCharacteristics(
        indication = Characteristic("Body Composition Measurement","00002A9C-0000-1000-8000-00805F9B34FB"),
        read = Characteristic("Body Composition Feature","00002A9B-0000-1000-8000-00805F9B34FB"),
        write = Characteristic("Current time","00002A2B-0000-1000-8000-00805F9B34FB"),
        notify = Characteristic("Body Composition History","00002A2F-0000-3512-2118-0009af100700")
    )
}
data class Characteristic(
    val name: String,
    val uuid: String
)

