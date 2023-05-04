package com.myproject.gymphysique.feature.settings

import com.myproject.gymphysique.core.common.Gender
import com.myproject.gymphysique.core.utils.UiText

internal data class SettingsState(
    val firstName: String = "",
    val surname: String = "",
    val height: Int = 0,
    val age: Int = 0,
    val gender: String = Gender.MALE.fullName,
    val saveUserDataResult: SaveUserDataResult = SaveUserDataResult.Initial
)

internal sealed class SaveUserDataResult {
    data class Success(val data: UiText) : SaveUserDataResult()
    data class Failure(val error: UiText) : SaveUserDataResult()
    object Initial : SaveUserDataResult()
}