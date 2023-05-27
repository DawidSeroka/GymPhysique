package com.myproject.gymphysique.accountsetup

import android.net.Uri
import com.myproject.gymphysique.core.common.SaveUserDataResult
import com.myproject.gymphysique.core.model.Gender

internal data class AccountSetupState(
    val firstName: String = "",
    val surname: String = "",
    val height: String = "",
    val age: String = "",
    val gender: Gender = Gender.OTHER,
    val firstnameError: Boolean = false,
    val surnameError: Boolean = false,
    val heightError: Boolean = false,
    val ageError: Boolean = false,
    val saveUserDataResult: SaveUserDataResult? = null,
    val expanded: Boolean = false,
    val selectedImageUri: Uri? = null,
    val navigateToGpApp: Boolean = false
)
