package com.myproject.gymphysique.feature.settings

import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.core.utils.UiText

internal data class SettingsState(
    val firstName: TextFieldValue = TextFieldValue(),
    val surname: TextFieldValue = TextFieldValue(),
    val height: TextFieldValue = TextFieldValue(),
    val age: TextFieldValue = TextFieldValue(),
    val gender: Gender = Gender.OTHER,
    val saveUserDataResult: SaveUserDataResult = SaveUserDataResult.Initial,
    val expanded: Boolean = false
)

internal sealed class SaveUserDataResult {
    data class Success(val data: UiText) : SaveUserDataResult()
    data class Failure(val error: UiText) : SaveUserDataResult()
    object Initial : SaveUserDataResult()
}
