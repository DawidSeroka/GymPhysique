package com.myproject.gymphysique.feature.settings.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.gymphysique.core.model.Gender

internal data class SettingsScreenActions(
    val onFirstNameChange: (TextFieldValue) -> Unit,
    val onSurnameChange: (TextFieldValue) -> Unit,
    val onHeightChange: (TextFieldValue) -> Unit,
    val onAgeChange: (TextFieldValue) -> Unit,
    val onGenderSelected: (Gender) -> Unit,
    val onDropdownSelected: () -> Unit,
    val onSaveSelected: () -> Unit,
    val onSaveUserDataResultReset: () -> Unit
)
