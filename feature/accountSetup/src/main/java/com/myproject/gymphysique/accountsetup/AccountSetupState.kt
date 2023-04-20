package com.myproject.gymphysique.accountsetup

internal data class AccountSetupState(
    val firstName: String = "",
    val surname: String = "",
    val isMale: Boolean? = null,
    val height: Int? = null,
    val age: Int? = null
)