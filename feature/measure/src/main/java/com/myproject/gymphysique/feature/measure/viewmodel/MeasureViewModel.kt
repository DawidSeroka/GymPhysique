package com.myproject.gymphysique.feature.measure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.feature.measure.AdvertisementStatus
import com.myproject.gymphysique.feature.measure.MeasureState
import com.myproject.gymphysqiue.core.domain.ProvideAdvertisementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MeasureViewModel @Inject constructor(
    private val provideAdvertisementsUseCase: ProvideAdvertisementsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MeasureState> = MutableStateFlow(MeasureState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    val state: StateFlow<MeasureState> = _state

    fun onSearchDevicesClick() {
        viewModelScope.launch {
            _state.update { it.copy(advertisementStatus = AdvertisementStatus.ADVERTISING) }
            withTimeoutOrNull(15_000) {
                provideAdvertisementsUseCase().map { advertisement ->
                   advertisement

                }.collect { adv ->
                    _state.update { it.copy(advertisements = it.advertisements + adv)}
                }
            }
            _state.update { it.copy(advertisementStatus = AdvertisementStatus.STOPPED) }
        }
    }
}