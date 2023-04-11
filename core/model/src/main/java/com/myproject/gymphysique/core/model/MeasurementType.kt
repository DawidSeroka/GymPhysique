package com.myproject.gymphysique.core.model

enum class MeasurementType(
    val serializedName: String,
    val fullName: String,
    val unit: MeasurementUnit
) {
    BODY_FAT(
        serializedName = "Body fat",
        fullName = "Body fat",
        unit = MeasurementUnit.PERCENTAGE
    ),
    BASAL_METABOLISM(
        serializedName = "Basal metabolism",
        fullName = "Basal metabolism",
        unit = MeasurementUnit.KILOJOULES
    ),
    MUSCLE_PERCENTAGE(
        serializedName = "Muscle percentage",
        fullName = "Muscle Percentage",
        unit = MeasurementUnit.PERCENTAGE
    ),
    MUSCLE_MASS(
        serializedName = "Muscle mass",
        fullName = "Muscle mass",
        unit = MeasurementUnit.KG
    ),
    FAT_FREE_MASS(
        serializedName = "Fat free mass",
        fullName = "Fat free mass",
        unit = MeasurementUnit.KG
    ),
    SOFT_LEAN_MASS(
        serializedName = "Soft lean mass",
        fullName = "Soft lean mass",
        unit = MeasurementUnit.KG
    ),
    BODY_WATER_MASS(
        serializedName = "Body water mass",
        fullName = "Body water mass",
        unit = MeasurementUnit.KG
    ),
    WEIGHT(
        serializedName = "Weight",
        fullName = "Weight",
        unit = MeasurementUnit.KG
    ),
    UNKNOWN(
        "",
        "",
        MeasurementUnit.UNKNOWN
    )
}

fun String?.asMeasurementType() = when(this){
    null -> MeasurementType.UNKNOWN
    else -> MeasurementType.values()
        .firstOrNull { type -> type.serializedName == this  } ?: MeasurementType.UNKNOWN
}