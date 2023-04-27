package com.myproject.gymphysique.feature.measure.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juul.kable.Advertisement
import com.juul.kable.Peripheral
import com.juul.kable.State
import com.juul.kable.characteristicOf
import com.juul.kable.logs.Hex
import com.juul.kable.logs.Logging
import com.juul.kable.peripheral
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.common.supportedServices.SupportedService
import com.myproject.gymphysique.core.common.toMillis
import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import com.myproject.gymphysique.feature.measure.AdvertisingStatus
import com.myproject.gymphysique.feature.measure.MeasureState
import com.myproject.gymphysique.feature.measure.PeripheralState
import com.myproject.gymphysqiue.core.domain.DecodeDataUseCase
import com.myproject.gymphysqiue.core.domain.ProvideAdvertisementsUseCase
import com.myproject.gymphysqiue.core.domain.TimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class MeasureViewModel @Inject constructor(
    private val provideAdvertisementsUseCase: ProvideAdvertisementsUseCase,
    private val timerUseCase: TimerUseCase,
    private val decodeDataUseCase: DecodeDataUseCase
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
                            _state.update { currentState ->
                                val advExists =
                                    currentState.advertisements
                                        .any { it.second.peripheralName == advertisement.peripheralName }
                                if (!advExists) {
                                    currentState.copy(
                                        advertisements = currentState.advertisements + Pair(
                                            PeripheralState.DISCONNECTED,
                                            advertisement
                                        )
                                    )
                                } else {
                                    currentState
                                }
                            }
                        }
                }
                timerAsync.await()
                advertisementsAsync.await()
            }
            _state.update {
                it.copy(
                    advertisingStatus = AdvertisingStatus.STOPPED,
                    scanTime = null
                )
            }
        }
    }

    internal fun onDisconnectClick() {
        viewModelScope.launch {
            async { observeJob?.cancel() }.await()
            async { _peripheral?.disconnect() }.await()
            _peripheral = null
            _state.update {
                it.copy(
                    measureState = false
                )
            }
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
                            withContext(Dispatchers.IO) {
                                val result = decodeDataUseCase(it)
                                if (result.isLoading()) {
                                    val bd =
                                        result.value() as ResponseData.BodyCompositionResponseData
                                    Timber.d("Result1 =$bd")
                                    val measurement = Measurement(
                                        measurementResult = bd.weight ?: 0.0,
                                        timestamp = bd.timestamp,
                                        measurementType = MeasurementType.WEIGHT
                                    )
                                    _state.update { it.copy(measurements = listOf(measurement)) }
                                    //TODO() //update ui
                                } else if (result.isSuccess()) {
                                    val measurementResponse =
                                        result.value() as ResponseData.BodyCompositionResponseData
                                    Timber.d("Result2 =${measurementResponse.bmi} ${measurementResponse.bodyFatPercentage}")
                                    val measurementWeight = Measurement(
                                        measurementResult = measurementResponse.weight ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.WEIGHT
                                    )
                                    val measurementFatPercentage = Measurement(
                                        measurementResult = measurementResponse.bodyFatPercentage
                                            ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.BODY_FAT
                                    )
                                    val measurementBmr = Measurement(
                                        measurementResult = measurementResponse.basalMetabolism
                                            ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.BASAL_METABOLISM
                                    )
                                    val measurementBoneMass = Measurement(
                                        measurementResult = measurementResponse.boneMass ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.BONE_MASS
                                    )
                                    val measurementMuscleMass = Measurement(
                                        measurementResult = measurementResponse.muscleMass ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.MUSCLE_MASS
                                    )
                                    val measurementMusclePercentage = Measurement(
                                        measurementResult = measurementResponse.musclePercentage
                                            ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.MUSCLE_PERCENTAGE
                                    )
                                    val measurementWaterPercentage = Measurement(
                                        measurementResult = measurementResponse.bodyWaterPercentage
                                            ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.BODY_WATER_MASS
                                    )
                                    val measurementVisceralFat = Measurement(
                                        measurementResult = measurementResponse.visceralFat ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.VISCERAL_FAT
                                    )
                                    val measurementIdealWeight = Measurement(
                                        measurementResult = measurementResponse.idealWeight ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.IDEAL_WEIGHT
                                    )
                                    val measurementBmi = Measurement(
                                        measurementResult = measurementResponse.bmi ?: 0.0,
                                        timestamp = measurementResponse.timestamp,
                                        measurementType = MeasurementType.BMI
                                    )
                                    _state.update {
                                        it.copy(
                                            measurements = listOf(
                                                measurementWeight,
                                                measurementBmi,
                                                measurementBmr,
                                                measurementBoneMass,
                                                measurementMuscleMass,
                                                measurementFatPercentage,
                                                measurementVisceralFat,
                                                measurementIdealWeight,
                                                measurementWaterPercentage,
                                                measurementMusclePercentage
                                            )
                                        )
                                    }
                                    //TODO() //update ui and cancel job
                                } else {
                                    val measurementResponse =
                                        result.value() as ResponseData.BodyCompositionResponseData
                                    Timber.d("Result3=$measurementResponse")

                                    onStopMeasureClick()
                                }
                            }
                        }
                    }
                }
            } ?: Timber.e("Characteristic is null!")
        }
    }

    internal fun onSaveMeasurementClick() {

    }

    internal fun onStopMeasureClick() {
        observeJob?.cancel()
        _state.update { it.copy(measureState = false) }
    }

    private suspend fun observeConnectState(peripheral: Peripheral, advertisement: Advertisement) {
        peripheral.state.collect { connectState ->
            _state.update { currentState ->
                when (connectState) {
                    State.Connected -> currentState.copy(
                        advertisements = listOf(
                            Pair(
                                PeripheralState.CONNECTED,
                                advertisement
                            )
                        )
                    )
                    is State.Connecting -> currentState.copy(
                        advertisements = listOf(
                            Pair(
                                PeripheralState.CONNECTING,
                                advertisement
                            )
                        )
                    )
                    is State.Disconnected -> {
                        onStopMeasureClick()
                        currentState.copy(
                            advertisements = listOf(
                                Pair(
                                    PeripheralState.DISCONNECTED,
                                    advertisement
                                )
                            )
                        )
                    }
                    else -> currentState.copy(
                        advertisements = listOf(
                            Pair(
                                PeripheralState.CONNECTING,
                                advertisement
                            )
                        )
                    )
                }

            }
        }
    }

}

const val SCAN_TIME = 10L