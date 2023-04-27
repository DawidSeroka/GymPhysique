package com.myproject.gymphysique.accountsetup.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.accountsetup.AccountSetupState
import com.myproject.gymphysique.core.common.Gender
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysqiue.core.domain.CreateUserUseCase
import com.myproject.gymphysqiue.core.domain.ObserveIfUserExistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class AccountSetupViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val observeIfUserExistsUseCase: ObserveIfUserExistsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AccountSetupState> = MutableStateFlow(AccountSetupState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000),
            {
                // if user exists then navigate to GpApp
                observeIfUserExistsUseCase()
                    .onEachToState { userExists, state ->
                        state.copy(navigateToGpApp = userExists)
                    }
            }
        )

    val state: StateFlow<AccountSetupState> = _state

    internal fun onFirstNameChange(firstName: TextFieldValue) {
        _state.update {
            it.copy(firstName = firstName)
        }
    }

    internal fun onSurnameChange(surname: TextFieldValue) {
        _state.update {
            it.copy(surname = surname)
        }
    }

    internal fun onGenderSelected(gender: Gender) {
        _state.update {
            it.copy(gender = gender, expanded = false)
        }
    }

    internal fun onHeightChange(height: TextFieldValue) {
        _state.update {
            it.copy(height = height)
        }
    }

    internal fun onAgeChange(age: TextFieldValue) {
        _state.update {
            it.copy(age = age)
        }
    }

    internal fun onDropdownSelected() {
        _state.update {
            it.copy(expanded = !it.expanded)
        }
    }

    internal fun onSaveUserClick() {
        val firstName = _state.value.firstName.text
        val surname = _state.value.surname.text
        val gender = _state.value.gender?.name ?: ""
        val height = _state.value.height.text.toInt()
        val age = _state.value.age.text.toInt()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                createUserUseCase(
                    firstName,
                    surname,
                    gender,
                    height,
                    age
                )
            }
        }
    }

    internal fun resetNavigateToGpApp() {
        _state.update { it.copy(navigateToGpApp = false) }
    }
    // internal fun isInputsValid()
}
