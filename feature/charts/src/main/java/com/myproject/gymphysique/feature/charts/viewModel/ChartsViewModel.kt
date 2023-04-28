package com.myproject.gymphysique.feature.charts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.model.MeasurementType
import com.myproject.gymphysique.feature.charts.ChartsState
import com.myproject.gymphysqiue.core.domain.charts.ObserveMeasurementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    observeMeasurementsUseCase: ObserveMeasurementsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ChartsState> = MutableStateFlow(ChartsState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000),
            {
                observeMeasurementsUseCase(state.value.selectedDate)
                    .onEachToState{ measurements, chartsState ->
                        val weightMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.WEIGHT }
                        val basalMetabolismMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.BASAL_METABOLISM }
                        val bmiMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.BMI }
                        val bodyFatMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.BODY_FAT }
                        val bodyWaterMassMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.BODY_WATER_MASS }
                        val boneMassMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.BONE_MASS }
                        val fatFreeMassMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.FAT_FREE_MASS }
                        val idealWeightMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.WEIGHT }
                        val muscleMassMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.WEIGHT }
                        val musclePercentageMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.WEIGHT }
                        val softLeanMassMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.WEIGHT }
                        val visceralFatMeasurements = measurements
                            .filter { it.measurementType == MeasurementType.WEIGHT }

                        chartsState.copy(
                            weightMeasurements = weightMeasurements,
                            basalMetabolismMeasurements = basalMetabolismMeasurements,
                            bmiMeasurements = bmiMeasurements,
                            bodyFatMeasurements = bodyFatMeasurements,
                            bodyWaterMassMeasurements = bodyWaterMassMeasurements,
                            boneMassMeasurements = boneMassMeasurements,
                            fatFreeMassMeasurements = fatFreeMassMeasurements,
                            idealWeightMeasurements = idealWeightMeasurements,
                            muscleMassMeasurements = muscleMassMeasurements,
                            musclePercentageMeasurements = musclePercentageMeasurements,
                            softLeanMassMeasurements = softLeanMassMeasurements,
                            visceralFatMeasurements = visceralFatMeasurements
                        )
                    }
            }
        )
val state: StateFlow<ChartsState> = _state
}