package com.myproject.gymphysique.accountsetup.viewModel

internal data class AccountSetupActions(
    val onFirstNameChange: (String) -> Unit,
    val onSurnameChange: (String) -> Unit,
    val onSexChange: (Boolean) -> Unit,
    val onHeightChange: (Int) -> Unit,
    val onAgeChange: (Int) -> Unit,
    val onSaveUserClick: () -> Unit
)