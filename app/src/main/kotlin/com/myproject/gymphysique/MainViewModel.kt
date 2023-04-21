package com.myproject.gymphysique

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.accountsetup.navigation.accountSetupNavigationRoute
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.model.UserData
import com.myproject.gymphysique.ui.gpAppNavigationRoute
import com.myproject.gymphysqiue.core.domain.ObserveIfUserExistsUseCase
import com.myproject.gymphysqiue.core.domain.ObserveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
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
                        .onEachToState { userExists, state ->
                            if (userExists)
                                state.copy(
                                    startDestination = gpAppNavigationRoute,
                                    downloadState = DownloadState.Success
                                ).also {
                                    Timber.d("state" + it.startDestination)
                                }
                            else
                                state.copy(
                                    startDestination = accountSetupNavigationRoute,
                                    downloadState = DownloadState.Success
                                ).also {
                                    Timber.d("state" + it.startDestination)
                                }
                        }
                }
            )
    val state: StateFlow<MainActivityUiState> = _state
}

internal data class MainActivityUiState(
    val downloadState: DownloadState = DownloadState.Loading,
    val startDestination: String? = null
)

internal sealed interface DownloadState {
    object Loading : DownloadState
    object Success : DownloadState
}