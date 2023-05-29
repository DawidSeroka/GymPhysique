package com.myproject.gymphysique.core.model

enum class MeasurementUnit(
    val serializedName: String,
    val unit: String
) {
    KG("KG", "kg"),
    PERCENTAGE("PERCENTAGE", "%"),
    KCAL("KCAL", "Kcal"),
    UNKNOWN("UNKNOWN", "")
}

fun String?.asMeasurementUnit() = when (this) {
    null -> MeasurementUnit.UNKNOWN
    else -> MeasurementUnit.values()
        .firstOrNull { type -> type.serializedName == this } ?: MeasurementUnit.UNKNOWN
}
