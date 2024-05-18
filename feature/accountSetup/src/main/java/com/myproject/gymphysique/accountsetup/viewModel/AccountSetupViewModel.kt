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
import com.myproject.gymphysique.accountsetup.R as AndroidR

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
        _state.update {
            it.copy(
                firstName = firstName,
                firstnameError = validateLength(
                    firstName,
                    Constants.FIRSTNAME_MIN_LENGTH,
                    Constants.FIRSTNAME_MAX_LENGTH
                )
            )
        }
    }

    internal fun onSurnameChange(surname: String) {
        _state.update {
            it.copy(
                surname = surname,
                surnameError = validateLength(
                    surname,
                    Constants.SURNAME_MIN_LENGTH,
                    Constants.SURNAME_MAX_LENGTH
                )
            )
        }
    }

    internal fun onHeightChange(height: String) {
        if (height.isNotEmpty()) {
            _state.update {
                it.copy(
                    height = height,
                    heightError = validateRange(
                        height.toIntOrNull(),
                        Constants.HEIGHT_MIN,
                        Constants.HEIGHT_MAX
                    )
                )
            }
        }
    }

    internal fun onAgeChange(age: String) {
        if (age.isNotEmpty()) {
            _state.update {
                it.copy(
                    age = age,
                    ageError = validateRange(
                        age.toIntOrNull(),
                        Constants.AGE_MIN,
                        Constants.AGE_MAX
                    )
                )
            }
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
                with(_state.value) {
                    saveUserDataUseCase(
                        firstName = firstName,
                        surname = surname,
                        height = height.toInt(),
                        age = age.toInt(),
                        gender = gender,
                        imageUri = selectedImageUri.toString() ?: ""
                    ).onSuccess { userData ->
                        _state.update {
                            it.copy(
                                saveUserDataResult = SaveUserDataResult.Success(
                                    UiText.StringResource(
                                        resId = AndroidR.string.account_setup_view_model_user_updated_successfully,
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
                                            AndroidR.string.account_setup_view_model_user_updated_failure,
                                            errorMessage
                                        )
                                    } ?: UiText.StringResource(
                                        AndroidR.string.account_setup_view_model_user_updated_failure_unknown_error
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    internal fun resetNavigateToGpApp() {
        _state.update { it.copy(navigateToGpApp = false) }
    }

    private fun validateLength(value: String, min: Int, max: Int) =
        value.length < min || value.length > max

    private fun validateRange(value: Int?, min: Int, max: Int) =
        value == null || value < min || value > max

    private fun checkIfTextFieldsHasErrors() =
        with(_state.value) {
            firstnameError || surnameError || ageError || heightError
        }

}
