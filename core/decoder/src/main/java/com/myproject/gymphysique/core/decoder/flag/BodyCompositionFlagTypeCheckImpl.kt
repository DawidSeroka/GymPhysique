package com.myproject.gymphysique.core.decoder.flag

import com.myproject.gymphysique.core.decoder.ResponseData
import javax.inject.Inject

class BodyCompositionFlagTypeCheckImpl @Inject constructor() : FlagTypeCheck.BodyCompositionFlagTypeCheck {
    override fun checkBodyCompositionFlagUnit(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_UNIT_IMPERIAL ==
                ResponseData.BodyCompositionResponseData.FLAG_UNIT_IMPERIAL


    override fun checkBodyCompositionFlagTimestamp(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_TIME_STAMP_PRESENT_BIT ==
                ResponseData.BodyCompositionResponseData.FLAG_TIME_STAMP_PRESENT_BIT


    override fun checkBodyCompositionFlagUserId(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_USER_ID_PRESENT_BIT ==
                ResponseData.BodyCompositionResponseData.FLAG_USER_ID_PRESENT_BIT


    override fun checkBodyCompositionFlagBasalMetabolism(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_BASAL_METABOLISM_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_BASAL_METABOLISM_PRESENT

    override fun checkBodyCompositionFlagMusclePercentage(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_MUSCLE_PERCENTAGE_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_MUSCLE_PERCENTAGE_PRESENT

    override fun checkBodyCompositionFlagMuscleMass(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_MUSCLE_MASS_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_MUSCLE_MASS_PRESENT

    override fun checkBodyCompositionFlagFatFreeMass(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_FAT_FREE_MASS_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_FAT_FREE_MASS_PRESENT

    override fun checkBodyCompositionFlagSoftLeanMass(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_SOFT_LEAN_MASS_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_SOFT_LEAN_MASS_PRESENT

    override fun checkBodyCompositionFlagBodyWaterMass(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_BODY_WATER_MASS_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_BODY_WATER_MASS_PRESENT

    override fun checkBodyCompositionFlagWeightPresent(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_WEIGHT_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_WEIGHT_PRESENT

    override fun checkBodyCompositionFlagImpedance(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_IMPEDANCE_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_IMPEDANCE_PRESENT

    override fun checkBodyCompositionFlagHeight(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_HEIGHT_PRESENT ==
                ResponseData.BodyCompositionResponseData.FLAG_HEIGHT_PRESENT

    override fun checkBodyCompositionFlagReset(flags: Int): Boolean =
        flags and ResponseData.BodyCompositionResponseData.FLAG_RESET ==
                ResponseData.BodyCompositionResponseData.FLAG_RESET
}