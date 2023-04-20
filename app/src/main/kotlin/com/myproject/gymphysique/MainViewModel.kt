package com.myproject.gymphysique

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.model.UserData
import com.myproject.gymphysqiue.core.domain.ObserveIfUserExistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    observeIfUserExistsUseCase: ObserveIfUserExistsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainActivityUiState> =
        MutableStateFlow(MainActivityUiState())
            .stateInMerge(
                scope = viewModelScope,
                launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000),
                {
                    observeIfUserExistsUseCase()
                        .onEachToState { userData, state ->
                            state.copy(downloadState = DownloadState.Success(userData))
                        }
                }
            )
    val state: StateFlow<MainActivityUiState> = _state
}

internal data class MainActivityUiState(
    val downloadState: DownloadState = DownloadState.Loading
)

internal sealed interface DownloadState {
    object Loading: DownloadState
    data class Success(val userData: UserData): DownloadState
}