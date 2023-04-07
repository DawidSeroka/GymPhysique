package com.myproject.gymphysique.feature.measure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juul.kable.Advertisement
import com.juul.kable.Peripheral
import com.juul.kable.State
import com.juul.kable.logs.Hex
import com.juul.kable.logs.Logging
import com.juul.kable.peripheral
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.feature.measure.AdvertisingStatus
import com.myproject.gymphysique.feature.measure.MeasureState
import com.myproject.gymphysique.feature.measure.PeripheralState
import com.myproject.gymphysqiue.core.domain.ProvideAdvertisementsUseCase
import com.myproject.gymphysqiue.core.domain.TimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
internal class MeasureViewModel @Inject constructor(
    private val provideAdvertisementsUseCase: ProvideAdvertisementsUseCase,
    private val timerUseCase: TimerUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MeasureState> = MutableStateFlow(MeasureState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    val state: StateFlow<MeasureState> = _state

    fun onSearchDevicesClick() {
        val scanTime = 10L
        viewModelScope.launch {
            _state.update { it.copy(advertisingStatus = AdvertisingStatus.ADVERTISING) }
            _state.update { it.copy(advertisements = emptyList()) }
            withTimeoutOrNull(10_000) {
                val res1 = async { timerUseCase(scanTime.toInt()).collect{scanTime ->
                    _state.update { it.copy(scanTime = scanTime) }
                } }
                val res2 =async {
                    provideAdvertisementsUseCase()
                        .collect { advertisement ->
                            _state.update { currentState ->
                                val advExists = currentState.advertisements.any { it.second.peripheralName == advertisement.peripheralName }
                                if (!advExists) {
                                    currentState.copy(
                                        advertisements = currentState.advertisements + Pair(PeripheralState.DISCONNECTED, advertisement)
                                    )
                                } else {
                                    currentState
                                }
                            }
                        }
                }
                res1.await()
                res2.await()
            }
            _state.update { it.copy(advertisingStatus = AdvertisingStatus.STOPPED) }
            _state.update { it.copy(scanTime = null) }
        }
    }

    fun onConnectDeviceClick(advertisement: Advertisement) {
        val peripheral = viewModelScope.peripheral(advertisement) {
            logging {
                level = Logging.Level.Events
                data = Hex {
                    separator = " "
                    lowerCase = false
                }
            }
        }
        viewModelScope.launch {
            peripheral.connect()
            observeConnectState(peripheral,advertisement)
        }
    }

    private suspend fun observeConnectState(peripheral: Peripheral, advertisement: Advertisement){
        peripheral.state.collect { connectState ->
            _state.update { currentState ->
                when(connectState){
                    State.Connected -> currentState.copy(advertisements = listOf(Pair(PeripheralState.CONNECTED, advertisement)))
                    is State.Connecting -> currentState.copy(advertisements = listOf(Pair(PeripheralState.CONNECTING, advertisement)))
                    is State.Disconnected -> currentState.copy(advertisements = listOf(Pair(PeripheralState.DISCONNECTED, advertisement)))
                    else -> currentState.copy(advertisements = listOf(Pair(PeripheralState.CONNECTING, advertisement)))
                }

            }
        }
    }
}