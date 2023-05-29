package com.myproject.gymphysique.accountsetup.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.accountsetup.AccountSetupState
import com.myproject.gymphysique.accountsetup.receiver.RemindersManager
import com.myproject.gymphysique.core.common.Constants
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.SaveUserDataResult
import com.myproject.gymphysique.core.common.UiText
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysqiue.core.domain.settings.SaveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AccountSetupViewModel @Inject constructor(
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val remindersManager: RemindersManager
) : ViewModel() {

    private val _state: MutableStateFlow<AccountSetupState> = MutableStateFlow(AccountSetupState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )

    val state: StateFlow<AccountSetupState> = _state

    internal fun onFirstNameChange(firstName: String) {
        val isError = Constants.FIRSTNAME_MIN_LENGTH > firstName.length || firstName.length > Constants.FIRSTNAME_MAX_LENGTH
        _state.update { it.copy(firstName = firstName, firstnameError = isError) }
    }

    internal fun onSurnameChange(surname: String) {
        val isError = Constants.SURNAME_MIN_LENGTH > surname.length || surname.length > Constants.SURNAME_MAX_LENGTH
        _state.update { it.copy(surname = surname, surnameError = isError) }
    }

    internal fun onHeightChange(height: String) {
        if (height.isNotEmpty()) {
            val isError = Constants.HEIGHT_MIN > height.toInt() || height.toInt() > Constants.HEIGHT_MAX
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
            remindersManager.startReminder()
            viewModelScope.launch {
                saveUserDataUseCase(
                    firstName = _state.value.firstName,
                    surname = _state.value.surname,
                    height = _state.value.height.toInt(),
                    age = _state.value.age.toInt(),
                    gender = _state.value.gender,
                    imageUri = _state.value.selectedImageUri.toString() ?: ""
                ).onSuccess { userData ->
                    _state.update {
                        it.copy(
                            saveUserDataResult = SaveUserDataResult.Success(
                                UiText.DynamicString(
                                    "Succesfully updated user: " +
                                        userData.firstName + " " + userData.surname
                                )
                            )
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            saveUserDataResult = SaveUserDataResult.Failure(
                                UiText.DynamicString(
                                    error.message?.let { errorMessage ->
                                        "Error: $errorMessage"
                                    } ?: "Unknown error occurred"
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun checkIfTextFieldsHasErrors(): Boolean {
        val firstNameError = _state.value.firstnameError
        val surnameError = _state.value.surnameError
        val ageError = _state.value.ageError
        val heightError = _state.value.heightError
        return firstNameError || surnameError || ageError || heightError
    }
    internal fun resetNavigateToGpApp() {
        _state.update { it.copy(navigateToGpApp = false) }
    }
}
