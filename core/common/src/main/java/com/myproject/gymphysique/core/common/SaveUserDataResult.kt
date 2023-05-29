package com.myproject.gymphysique.core.common

sealed class SaveUserDataResult(val message: UiText) {
    data class Success(val data: UiText) : SaveUserDataResult(message = data)
    data class Failure(val error: UiText) : SaveUserDataResult(message = error)
}
