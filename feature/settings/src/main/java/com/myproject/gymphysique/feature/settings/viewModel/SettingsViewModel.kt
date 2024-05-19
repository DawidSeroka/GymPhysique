package com.myproject.gymphysique.feature.settings.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Constants
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.SaveUserDataResult
import com.myproject.gymphysique.core.common.UiText
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.core.model.toGender
import com.myproject.gymphysique.feature.settings.SettingsState
import com.myproject.gymphysqiue.core.domain.settings.GetUserUseCase
import com.myproject.gymphysqiue.core.domain.settings.SaveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.myproject.gymphysique.feature.settings.R as SettingsR

@Suppress("LongMethod")
@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val getUserUseCase: GetUserUseCase
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
                    firstName = user.firstName,
                    surname = user.surname,
                    height = user.height.toString(),
                    age = user.age.toString(),
                    gender = user.gender.toGender(),
                    selectedImageUri = Uri.parse(user.uri)
                )
            }
        }
    }

    internal fun onFirstNameChange(firstName: String) {
        val isError =
            Constants.FIRSTNAME_MIN_LENGTH > firstName.length || firstName.length > Constants.FIRSTNAME_MAX_LENGTH
        _state.update { it.copy(firstName = firstName, firstnameError = isError) }
    }

    internal fun onSurnameChange(surname: String) {
        val isError =
            Constants.SURNAME_MIN_LENGTH > surname.length || surname.length > Constants.SURNAME_MAX_LENGTH
        _state.update { it.copy(surname = surname, surnameError = isError) }
    }

    internal fun onHeightChange(height: String) {
        if (height.isNotEmpty()) {
            val isError =
                Constants.HEIGHT_MIN > height.toInt() || height.toInt() > Constants.HEIGHT_MAX
            _state.update { it.copy(height = height, heightError = isError) }
        }
    }

    internal fun onAgeChange(age: String) {
        if (age.isNotEmpty()) {
            val isError = Constants.AGE_MIN > age.toInt() || age.toInt() >= Constants.AGE_MAX
            _state.update { it.copy(age = age, ageError = isError) }
        }
    }

    internal fun onGenderSelected(gender: Gender) {
        _state.update { it.copy(gender = gender, expanded = false) }
    }

    internal fun onSaveUserDataResultReset() {
        _state.update { it.copy(saveUserDataResult = null) }
    }

    internal fun onDropdownSelected() {
        _state.update { it.copy(expanded = !it.expanded) }
    }

    internal fun onImageUriSelected(uri: Uri?) {
        _state.update { it.copy(selectedImageUri = uri) }
    }

    internal fun onSaveSelected() {
        if (!checkIfTextFieldsHasErrors()) {
            viewModelScope.launch {
                with(_state.value) {
                    saveUserDataUseCase(
                        firstName = firstName,
                        surname = surname,
                        height = height.toInt(),
                        age = age.toInt(),
                        gender = gender,
                        imageUri = selectedImageUri.toString()
                    ).onSuccess { userData ->
                        _state.update {
                            it.copy(
                                saveUserDataResult = SaveUserDataResult.Success(
                                    UiText.StringResource(
                                        resId = SettingsR.string.user_updated_successfully,
                                        args = arrayOf(userData.firstName, userData.surname)
                                    )
                                )
                            )
                        }
                    }.onFailure { error ->
                        _state.update {
                            it.copy(
                                saveUserDataResult = SaveUserDataResult.Failure(
                                    error.message?.let { errorMessage ->
                                        UiText.StringResource(
                                            SettingsR.string.user_updated_failure,
                                            errorMessage
                                        )
                                    } ?: UiText.StringResource(
                                        SettingsR.string.user_updated_failure_unknown_error
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkIfTextFieldsHasErrors(): Boolean =
        with(_state.value) {
            val firstNameError = firstnameError
            val surnameError = surnameError
            val ageError = ageError
            val heightError = heightError
            firstNameError || surnameError || ageError || heightError
        }
}
