package com.myproject.gymphysique.core.decoder.flag

sealed interface FlagTypeCheck {
    interface BodyCompositionFlagTypeCheck : FlagTypeCheck {
        fun checkBodyCompositionFlagUnit(flags: Int): Boolean
        fun checkBodyCompositionFlagTimestamp(flags: Int): Boolean
        fun checkBodyCompositionFlagUserId(flags: Int): Boolean
        fun checkBodyCompositionFlagBasalMetabolism(flags: Int): Boolean
        fun checkBodyCompositionFlagMusclePercentage(flags: Int): Boolean
        fun checkBodyCompositionFlagMuscleMass(flags: Int): Boolean
        fun checkBodyCompositionFlagFatFreeMass(flags: Int): Boolean
        fun checkBodyCompositionFlagSoftLeanMass(flags: Int): Boolean
        fun checkBodyCompositionFlagBodyWaterMass(flags: Int): Boolean
        fun checkBodyCompositionFlagWeightPresent(flags: Int): Boolean
        fun checkBodyCompositionFlagImpedance(flags: Int): Boolean
        fun checkBodyCompositionFlagHeight(flags: Int): Boolean
        fun checkBodyCompositionFlagReset(flags: Int): Boolean
    }
}
