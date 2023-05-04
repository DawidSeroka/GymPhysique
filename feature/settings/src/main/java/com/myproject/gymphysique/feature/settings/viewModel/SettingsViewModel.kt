package com.myproject.gymphysique.feature.settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Gender
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.common.toGender
import com.myproject.gymphysique.core.utils.UiText
import com.myproject.gymphysique.feature.settings.SaveUserDataResult
import com.myproject.gymphysique.feature.settings.SettingsState
import com.myproject.gymphysqiue.core.domain.app.ObserveUserUseCase
import com.myproject.gymphysqiue.core.domain.settings.GetUserUseCase
import com.myproject.gymphysqiue.core.domain.settings.SaveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            _state.update { it.copy(
                firstName = user.firstName,
                surname = user.surname,
                height = user.height,
                age = user.age,
                gender = user.gender
            ) }
        }
    }

    internal fun onFirstNameChange(firstName: String) {
        _state.update { it.copy(firstName = firstName) }
    }

    internal fun onSurnameChange(surname: String) {
        _state.update { it.copy(surname = surname) }
    }

    internal fun onHeightChange(height: Int) {
        _state.update { it.copy(height = height) }
    }

    internal fun onAgeChange(age: Int) {
        _state.update { it.copy(age = age) }
    }

    internal fun onGenderChange(gender: String) {
        _state.update { it.copy(gender = gender) }
    }

    internal fun onSaveUserDataResultReset(){
        _state.update { it.copy(saveUserDataResult = SaveUserDataResult.Initial) }
    }

    internal fun onSaveSelected() {
        viewModelScope.launch {
            saveUserDataUseCase(
                firstName = _state.value.firstName,
                surname = _state.value.surname,
                height = _state.value.height,
                age = _state.value.age,
                gender = _state.value.gender.toGender()
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
            }
        }
    }
}
