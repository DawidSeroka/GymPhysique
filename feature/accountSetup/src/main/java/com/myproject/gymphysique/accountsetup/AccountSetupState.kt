package com.myproject.gymphysique.accountsetup

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.gymphysique.core.common.SaveUserDataResult
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysqiue.core.domain.util.ValidateResult

internal data class AccountSetupState(
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
    val selectedImageUri: Uri? = null,
    val navigateToGpApp: Boolean = false
)
