package com.myproject.gymphysique.feature.settings.viewModel

import com.myproject.gymphysique.core.common.Gender

internal data class SettingsScreenActions(
    val onFirstNameChange: (String) -> Unit,
    val onSurnameChange: (String) -> Unit,
    val onHeightChange: (Int) -> Unit,
    val onAgeChange: (Int) -> Unit,
    val onGenderChange: (String) -> Unit,
    val onSaveSelected: () -> Unit,
    val onSaveUserDataResultReset: () -> Unit
)
