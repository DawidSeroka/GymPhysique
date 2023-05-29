package com.myproject.gymphysique.feature.settings.viewModel

import android.net.Uri
import com.myproject.gymphysique.core.model.Gender

internal data class SettingsScreenActions(
    val onFirstNameChange: (String) -> Unit,
    val onSurnameChange: (String) -> Unit,
    val onHeightChange: (String) -> Unit,
    val onAgeChange: (String) -> Unit,
    val onGenderSelected: (Gender) -> Unit,
    val onDropdownSelected: () -> Unit,
    val onSaveSelected: () -> Unit,
    val onSaveUserDataResultReset: () -> Unit,
    val onImageUriSelected: (Uri?) -> Unit
)
