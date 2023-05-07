package com.myproject.gymphysique.core.common

sealed class SaveUserDataResult {
    data class Success(val data: UiText) : SaveUserDataResult()
    data class Failure(val error: UiText) : SaveUserDataResult()
    object Initial : SaveUserDataResult()
}
