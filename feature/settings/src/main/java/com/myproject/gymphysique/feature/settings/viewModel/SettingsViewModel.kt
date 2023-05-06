package com.myproject.gymphysique.feature.settings.viewModel

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.core.model.toGender
import com.myproject.gymphysique.core.utils.UiText
import com.myproject.gymphysique.feature.settings.SaveUserDataResult
import com.myproject.gymphysique.feature.settings.SettingsState
import com.myproject.gymphysqiue.core.domain.settings.GetUserUseCase
import com.myproject.gymphysqiue.core.domain.settings.SaveUserDataUseCase
import com.myproject.gymphysqiue.core.domain.util.TextFieldType
import com.myproject.gymphysqiue.core.domain.util.ValidateResult
import com.myproject.gymphysqiue.core.domain.util.ValidateTextFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val validateTextFieldUseCase: ValidateTextFieldUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    val state: StateFlow<SettingsState> = _state

    init {
        viewModelScope.launch {
            val user = getUserUseCase()
            _state.update {
                it.copy(
                    firstName = TextFieldValue(user.firstName),
                    surname = TextFieldValue(user.surname),
                    height = TextFieldValue(user.height.toString()),
                    age = TextFieldValue(user.age.toString()),
                    gender = user.gender.toGender(),
                    selectedImageUri = Uri.parse(user.uri)
                )
            }
        }
    }

    internal fun onFirstNameChange(firstName: TextFieldValue) {
        _state.update { it.copy(firstName = firstName) }
    }

    internal fun onSurnameChange(surname: TextFieldValue) {
        _state.update { it.copy(surname = surname) }
    }

    internal fun onHeightChange(height: TextFieldValue) {
        _state.update { it.copy(height = height) }
    }

    internal fun onAgeChange(age: TextFieldValue) {
        _state.update { it.copy(age = age) }
    }

    internal fun onGenderSelected(gender: Gender) {
        _state.update { it.copy(gender = gender, expanded = false) }
    }

    internal fun onSaveUserDataResultReset() {
        _state.update { it.copy(saveUserDataResult = SaveUserDataResult.Initial) }
    }

    internal fun onDropdownSelected() {
        _state.update { it.copy(expanded = !it.expanded) }
    }

    internal fun onValidateResultReset() {
        _state.update { it.copy(validateResult = ValidateResult.Correct) }
    }

    internal fun onImageUriSelected(uri: Uri?) {
        _state.update { it.copy(selectedImageUri = uri) }
    }

    internal fun onSaveSelected() {
        resetErrorStates()
        viewModelScope.launch {
            val firstnameValidateResult =
                validateTextFieldUseCase(TextFieldType.Firstname(state.value.firstName.text))
            val surnameValidateResult =
                validateTextFieldUseCase(TextFieldType.Firstname(state.value.surname.text))
            val heightValidateResult =
                validateTextFieldUseCase(TextFieldType.Height(state.value.height.text.toInt()))
            val ageValidateResult =
                validateTextFieldUseCase(TextFieldType.Age(state.value.age.text.toInt()))
            if (firstnameValidateResult is ValidateResult.Error) {
                _state.update { it.copy(validateResult = firstnameValidateResult, firstnameError = true) }
            } else if (surnameValidateResult is ValidateResult.Error) {
                _state.update { it.copy(validateResult = surnameValidateResult, surnameError = true) }
            } else if (heightValidateResult is ValidateResult.Error) {
                _state.update { it.copy(validateResult = heightValidateResult, heightError = true) }
            } else if (ageValidateResult is ValidateResult.Error) {
                _state.update { it.copy(validateResult = ageValidateResult, ageError = true) }
            } else {
                saveUserDataUseCase(
                    firstName = _state.value.firstName.text,
                    surname = _state.value.surname.text,
                    height = _state.value.height.text.toInt(),
                    age = _state.value.age.text.toInt(),
                    gender = _state.value.gender,
                    imageUri = _state.value.selectedImageUri.toString() ?: ""
                ).onSuccess { userData ->
                    _state.update {
                        it.copy(
                            saveUserDataResult = SaveUserDataResult.Success(
                                UiText.DynamicString(userData.firstName + " " + userData.surname)
                            )
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            saveUserDataResult = SaveUserDataResult.Failure(
                                UiText.DynamicString(error.message ?: "Unknown")
                            )
                        )
                    }
                }.also {
                    resetErrorStates()
                }
            }
        }
    }
    private fun resetErrorStates() {
        _state.update {
            it.copy(
                validateResult = ValidateResult.Correct,
                firstnameError = false,
                surnameError = false,
                heightError = false,
                ageError = false
            )
        }
    }
}
