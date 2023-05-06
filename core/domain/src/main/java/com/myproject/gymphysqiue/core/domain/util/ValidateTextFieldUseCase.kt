package com.myproject.gymphysqiue.core.domain.util

import javax.inject.Inject

class ValidateTextFieldUseCase @Inject constructor() : (TextFieldType) -> ValidateResult {
    override fun invoke(textFieldType: TextFieldType): ValidateResult {
        return when (textFieldType) {
            is TextFieldType.Firstname -> {
                if (textFieldType.value.length <= 2) {
                    ValidateResult.Error("Firstname must have more than 2 chars!")
                } else if (textFieldType.value.length >= 20) {
                    ValidateResult.Error("Firstname must have less than 20 chars!")
                } else {
                    ValidateResult.Correct
                }
            }
            is TextFieldType.Surname -> {
                if (textFieldType.value.length <= 2) {
                    ValidateResult.Error("Firstname must have more than 2 chars!")
                } else if (textFieldType.value.length >= 20) {
                    ValidateResult.Error("Firstname must have less than 20 chars!")
                } else {
                    ValidateResult.Correct
                }
            }
            is TextFieldType.Age -> {
                if (textFieldType.value < 1) {
                    ValidateResult.Error("Age must be greater than 0!")
                } else if (textFieldType.value >= 120) {
                    ValidateResult.Error("Age must be smaller than 120!")
                } else {
                    ValidateResult.Correct
                }
            }
            is TextFieldType.Height -> {
                if (textFieldType.value <= 100) {
                    ValidateResult.Error("Height must be greater than 100!")
                } else if (textFieldType.value >= 200) {
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
