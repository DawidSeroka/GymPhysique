package com.myproject.gymphysique.feature.settings

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.core.utils.UiText
import com.myproject.gymphysqiue.core.domain.util.ValidateResult

internal data class SettingsState(
    val firstName: TextFieldValue = TextFieldValue(),
    val surname: TextFieldValue = TextFieldValue(),
    val height: TextFieldValue = TextFieldValue(),
    val age: TextFieldValue = TextFieldValue(),
    val gender: Gender = Gender.OTHER,
    val firstnameError: Boolean = false,
    val surnameError: Boolean = false,
    val heightError: Boolean = false,
    val ageError: Boolean = false,
    val validateResult: ValidateResult = ValidateResult.Correct,
    val saveUserDataResult: SaveUserDataResult = SaveUserDataResult.Initial,
    val expanded: Boolean = false,
    val selectedImageUri: Uri? = null
)

internal sealed class SaveUserDataResult {
    data class Success(val data: UiText) : SaveUserDataResult()
    data class Failure(val error: UiText) : SaveUserDataResult()
    object Initial : SaveUserDataResult()
}
