package com.myproject.gymphysique.accountsetup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.accountsetup.AccountSetupState
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysqiue.core.domain.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class AccountSetupViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AccountSetupState> = MutableStateFlow(AccountSetupState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
            )

    val state: StateFlow<AccountSetupState> = _state

    internal fun onFirstNameChange(firstName: String){
        _state.update {
            it.copy(firstName = firstName)
        }
    }

    internal fun onSurnameChange(surname: String){
        _state.update {
            it.copy(surname = surname)
        }
    }

    internal fun onSexChange(isMale: Boolean){
        _state.update {
            it.copy(isMale = isMale)
        }
    }

    internal fun onHeightChange(height: Int){
        _state.update {
            it.copy(height = height)
        }
    }

    internal fun onAgeChange(age: Int){
        _state.update {
            it.copy(age = age)
        }
    }

    internal fun onSaveUserClick(){
        val firstName = _state.value.firstName!!
        val surname = _state.value.surname!!
        val isMale = _state.value.isMale!!
        val height = _state.value.height!!
        val age = _state.value.age!!
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                createUserUseCase(
                    firstName,
                    surname,
                    isMale,
                    height,
                    age
                )
            }
        }
    }
}