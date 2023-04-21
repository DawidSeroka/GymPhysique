package com.myproject.gymphysique.core.decoder

sealed interface ResponseData{
    data class BodyCompositionResponseData(
        val weight: Double?,
        val unit: String = "kg",
        val bodyFatPercentage: Double? = null,
        val timestamp: Long,
        val basalMetabolism: Double? = null,
        val musclePercentage: Double? = null,
        val muscleMass: Double? = null,
        val visceralFat: Double? = null,
        val softLeanMass: Double? = null,
        val bodyWaterPercentage: Double? = null,
        val boneMass: Double? = null,
        val idealWeight: Double? = null,
        val bmi: Double? = null,
        val impedance: Double? = null,
        val userId: Int? = null,
        val height: Int? = null
    ) : ResponseData {
        companion object Flags {
            const val FLAG_UNIT_IMPERIAL = 0x01
            const val FLAG_TIME_STAMP_PRESENT_BIT = 0x02
            const val FLAG_USER_ID_PRESENT_BIT = 0x04
            const val FLAG_BASAL_METABOLISM_PRESENT = 0x08
            const val FLAG_MUSCLE_PERCENTAGE_PRESENT = 0x10
            const val FLAG_MUSCLE_MASS_PRESENT = 0x20
            const val FLAG_FAT_FREE_MASS_PRESENT = 0x40
            const val FLAG_SOFT_LEAN_MASS_PRESENT = 0x80
            const val FLAG_BODY_WATER_MASS_PRESENT = 0x100
            const val FLAG_IMPEDANCE_PRESENT = 0x200
            const val FLAG_WEIGHT_PRESENT = 0x400
            const val FLAG_HEIGHT_PRESENT = 0x800
            const val FLAG_RESET = 0x8000
        }
    }
}