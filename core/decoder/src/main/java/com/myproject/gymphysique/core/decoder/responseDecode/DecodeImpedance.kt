package com.myproject.gymphysique.core.decoder.responseDecode

import javax.inject.Inject

@Suppress("MagicNumber")
class DecodeImpedance @Inject constructor() {

    internal fun checkValueOverflow(value: Double, minimum: Double, maximum: Double): Double {
        return when {
            value < minimum -> minimum
            value > maximum -> maximum
            else -> value
        }
    }

    internal fun getLBMCoefficient(weight: Double, height: Int, impedance: Int, age: Int): Double {
        var lbm = (height * 9.058 / 100) * (height / 100)
        lbm += weight * 0.32 + 12.226
        lbm -= impedance * 0.0068
        lbm -= age * 0.0542
        return lbm
    }
}
