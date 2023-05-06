package com.myproject.gymphysique.accountsetup

import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.gymphysique.core.model.Gender

internal data class AccountSetupState(
    val firstName: TextFieldValue = TextFieldValue(),
    val surname: TextFieldValue = TextFieldValue(),
    val gender: Gender? = null,
    val height: TextFieldValue = TextFieldValue(),
    val age: TextFieldValue = TextFieldValue(),
    val expanded: Boolean = false,
    val navigateToGpApp: Boolean = false
)
