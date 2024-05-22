package com.myproject.gymphysique.feature.measure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juul.kable.Advertisement
import com.juul.kable.Peripheral
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Hex
import com.juul.kable.logs.Logging
import com.juul.kable.peripheral
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.UiText
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.common.supportedServices.SupportedService
import com.myproject.gymphysique.core.common.toMillis
import com.myproject.gymphysique.core.model.ConnectionState
import com.myproject.gymphysique.feature.measure.AdvertisementWrapper
import com.myproject.gymphysique.feature.measure.AdvertisingStatus
import com.myproject.gymphysique.feature.measure.MeasureState
import com.myproject.gymphysique.feature.measure.SaveOperationResult
import com.myproject.gymphysqiue.core.domain.decode.DecodeDataUseCase
import com.myproject.gymphysqiue.core.domain.measure.AddMeasurementUseCase
import com.myproject.gymphysqiue.core.domain.measure.ObserveConnectStateUseCase
import com.myproject.gymphysqiue.core.domain.measure.ProvideAdvertisementsUseCase
import com.myproject.gymphysqiue.core.domain.measure.TimerUseCase
import com.myproject.gymphysqiue.core.domain.measure.ValidateCurrentAdvertisementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import javax.inject.Inject
import com.myproject.gymphysique.feature.measurements.R as MeasurementsR

@HiltViewModel
internal class MeasureViewModel @Inject constructor(
    private val provideAdvertisementsUseCase: ProvideAdvertisementsUseCase,
    private val observeConnectStateUseCase: ObserveConnectStateUseCase,
    private val validateCurrentAdvertisementsUseCase: ValidateCurrentAdvertisementsUseCase,
    private val timerUseCase: TimerUseCase,
    private val decodeDataUseCase: DecodeDataUseCase,
    private val addMeasurementUseCase: AddMeasurementUseCase
) : ViewModel() {
    private var _peripheral: Peripheral? = null
    private var observeJob: Job? = null

    private val _state: MutableStateFlow<MeasureState> = MutableStateFlow(MeasureState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    val state: StateFlow<MeasureState> = _state

    internal fun onSearchDevicesClick() {
        _peripheral?.let {
            onDisconnectClick()
        }
        scanAdvertisements()
    }

    private fun scanAdvertisements() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    advertisements = emptyList(),
                    advertisingStatus = AdvertisingStatus.ADVERTISING,
                    measurements = emptyList()
                )
            }
            withTimeoutOrNull(SCAN_TIME.toMillis()) {
                val timerAsync = async {
                    timerUseCase(SCAN_TIME.toInt()).collect { scanTime ->
                        _state.update { it.copy(scanTime = scanTime) }
                    }
                }
                val advertisementsAsync = async {
                    provideAdvertisementsUseCase()
                        .collect { advertisement ->
                            val currentAdvertisements = state.value.advertisements
                            val newAdvertisementList = validateCurrentAdvertisementsUseCase(
                                advertisement,
                                currentAdvertisements.map { it.advertisement }
                            ).map { AdvertisementWrapper(ConnectionState.DISCONNECTED, it) }
                            _state.update { currentState ->
                                currentState.copy(advertisements = newAdvertisementList)
                            }
                        }
                }
                timerAsync.await()
                advertisementsAsync.await()
            }
            _state.update {
                it.copy(advertisingStatus = AdvertisingStatus.STOPPED, scanTime = null)
            }
        }
    }

    internal fun onDisconnectClick() {
        viewModelScope.launch {
            async { observeJob?.cancel() }.await()
            async { _peripheral?.disconnect() }.await()
            _peripheral = null
            _state.update { it.copy(measureState = false) }
        }
    }

    internal fun onConnectDeviceClick(advertisement: Advertisement) {
        _peripheral = viewModelScope.peripheral(advertisement) {
            logging {
                level = Logging.Level.Events
                data = Hex {
                    separator = " "
                    lowerCase = false
                }
            }
        }
        viewModelScope.launch {
            _peripheral?.let {
                it.connect()
                observeConnectState(it, advertisement)
            }
        }
    }

    internal fun onSearchMeasurementsClick() {
        val indicationCharacteristic =
            SupportedService.BODY_COMPOSITION.characteristics.characteristics.indication?.uuid
        _peripheral?.let { peripheral ->
            indicationCharacteristic?.let { characteristic ->
                viewModelScope.launch {
                    _state.update { it.copy(measureState = true) }
                    val bleCharacteristicObject = characteristicOf(
                        service = SupportedService.BODY_COMPOSITION.uuid,
                        characteristic = characteristic
                    )
                    val byteArray = peripheral.observe(
                        characteristic = bleCharacteristicObject
                    ).cancellable()
                    observeJob = viewModelScope.launch {
                        byteArray.collect {
                            val measurements = decodeDataUseCase(it)
                            if (measurements.size > 1) {
                                _state.update { measureState ->
                                    measureState.copy(
                                        measurements = measurements,
                                        measureState = false
                                    )
                                }
                            } else {
                                _state.update { measureState -> measureState.copy(measurements = measurements) }
                            }
                        }
                    }
                }
            } ?: Timber.e("Characteristic is null!")
        }
    }

    internal fun onSaveMeasurementClick() {
        val measurements = _state.value.measurements
        measurements.forEach { measurement ->
            viewModelScope.launch {
                val result = addMeasurementUseCase(measurement)
                if (result >= 0) {
                    _state.update {
                        it.copy(
                            saveMeasurementResult = SaveOperationResult.Success(
                                UiText.StringResource(MeasurementsR.string.measurement_successfully_added)
                            ),
                            measurements = emptyList()
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            saveMeasurementResult = SaveOperationResult.Error(
                                UiText.StringResource(MeasurementsR.string.measurement_addding_failure)
                            ),
                            measurements = emptyList()
                        )
                    }
                }
            }
        }
    }

    internal fun onStopMeasureClick() {
        observeJob?.cancel()
        _state.update { it.copy(measureState = false) }
    }

    internal fun onSaveMeasurementResultReset() {
        _state.update { it.copy(saveMeasurementResult = null) }
    }

    private suspend fun observeConnectState(peripheral: Peripheral, advertisement: Advertisement) {
        peripheral.state.collect { connectState ->
            val connectionState = observeConnectStateUseCase(connectState)
            if (connectionState == ConnectionState.DISCONNECTED)
                onStopMeasureClick()
            _state.update { currentState ->
                currentState.copy(
                    advertisements = listOf(
                        AdvertisementWrapper(
                            connectionState,
                            advertisement
                        )
                    )
                )
            }
        }
    }

    private companion object {
        const val SCAN_TIME = 10L
    }
}
