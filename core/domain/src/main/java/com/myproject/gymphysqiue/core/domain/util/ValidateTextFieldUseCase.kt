package com.myproject.gymphysqiue.core.domain.util

import com.myproject.gymphysqiue.core.domain.util.Constants.ageMaxValue
import com.myproject.gymphysqiue.core.domain.util.Constants.ageMinValue
import com.myproject.gymphysqiue.core.domain.util.Constants.firstnameMaxLenght
import com.myproject.gymphysqiue.core.domain.util.Constants.firstnameMinLenght
import com.myproject.gymphysqiue.core.domain.util.Constants.heightMaxValue
import com.myproject.gymphysqiue.core.domain.util.Constants.heightMinValue
import com.myproject.gymphysqiue.core.domain.util.Constants.surnameMaxLenght
import com.myproject.gymphysqiue.core.domain.util.Constants.surnameMinLenght
import javax.inject.Inject

class ValidateTextFieldUseCase @Inject constructor() : (TextFieldType) -> ValidateResult {
    override fun invoke(textFieldType: TextFieldType): ValidateResult {
        return when (textFieldType) {
            is TextFieldType.Firstname -> {
                if (textFieldType.value.length <= firstnameMinLenght) {
                    ValidateResult.Error("Firstname must have more than 2 chars!")
                } else if (textFieldType.value.length >= firstnameMaxLenght) {
                    ValidateResult.Error("Firstname must have less than 20 chars!")
                } else {
                    ValidateResult.Correct
                }
            }
            is TextFieldType.Surname -> {
                if (textFieldType.value.length <= surnameMinLenght) {
                    ValidateResult.Error("Firstname must have more than 2 chars!")
                } else if (textFieldType.value.length >= surnameMaxLenght) {
                    ValidateResult.Error("Firstname must have less than 20 chars!")
                } else {
                    ValidateResult.Correct
                }
            }
            is TextFieldType.Age -> {
                if (textFieldType.value < ageMinValue) {
                    ValidateResult.Error("Age must be greater than 0!")
                } else if (textFieldType.value >= ageMaxValue) {
                    ValidateResult.Error("Age must be smaller than 120!")
                } else {
                    ValidateResult.Correct
                }
            }
            is TextFieldType.Height -> {
                if (textFieldType.value <= heightMinValue) {
                    ValidateResult.Error("Height must be greater than 100!")
                } else if (textFieldType.value >= heightMaxValue) {
                    ValidateResult.Error("Height must be smaller than 200!")
                } else {
                    ValidateResult.Correct
                }
            }
        }
    }
}

sealed interface TextFieldType {
    data class Firstname(val value: String) : TextFieldType
    data class Surname(val value: String) : TextFieldType
    data class Height(val value: Int) : TextFieldType
    data class Age(val value: Int) : TextFieldType
}

sealed interface ValidateResult {
    object Correct : ValidateResult
    data class Error(val message: String) : ValidateResult
}

@Suppress("TopLevelPropertyNaming")
private object Constants {
    const val firstnameMinLenght = 2
    const val surnameMinLenght = 2
    const val firstnameMaxLenght = 20
    const val surnameMaxLenght = 20
    const val ageMinValue = 1
    const val heightMinValue = 100
    const val ageMaxValue = 120
    const val heightMaxValue = 200
}
