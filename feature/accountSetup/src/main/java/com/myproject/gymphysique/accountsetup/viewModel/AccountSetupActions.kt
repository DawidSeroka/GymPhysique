package com.myproject.gymphysique.accountsetup.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.gymphysique.core.model.Gender

internal data class AccountSetupActions(
    val onFirstNameChange: (TextFieldValue) -> Unit,
    val onSurnameChange: (TextFieldValue) -> Unit,
    val onGenderSelected: (Gender) -> Unit,
    val onHeightChange: (TextFieldValue) -> Unit,
    val onAgeChange: (TextFieldValue) -> Unit,
    val onDropdownSelected: () -> Unit,
    val onSaveUserClick: () -> Unit
)
