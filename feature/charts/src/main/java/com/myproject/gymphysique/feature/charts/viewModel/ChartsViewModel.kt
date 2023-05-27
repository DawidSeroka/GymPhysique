package com.myproject.gymphysique.feature.charts.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.gymphysique.core.common.Launched
import com.myproject.gymphysique.core.common.stateInMerge
import com.myproject.gymphysique.core.common.toMonthAndYear
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import com.myproject.gymphysique.feature.charts.ChartsState
import com.myproject.gymphysqiue.core.domain.charts.GetMeasurementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChartsViewModel @Inject constructor(
    private val getMeasurementsUseCase: GetMeasurementsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<ChartsState> = MutableStateFlow(ChartsState())
        .stateInMerge(
            scope = viewModelScope,
            launched = Launched.WhileSubscribed(stopTimeoutMillis = 5_000)
        )
    val state: StateFlow<ChartsState> = _state

    init {
        viewModelScope.launch {
            _state.update { it.copy(measurements = getMeasurements()) }
        }
    }

    internal fun onMeasurementTypeSelected(measurementType: MeasurementType) {
        _state.update {
            it.copy(
                selectedMeasurementType = measurementType,
                dropdownMeasurementTypeExpanded = false
            )
        }.also {
            viewModelScope.launch {
                _state.update { it.copy(measurements = getMeasurements()) }
            }
        }
    }

    internal fun onMeasurementTypeDropdownSelected() {
        _state.update { it.copy(dropdownMeasurementTypeExpanded = !it.dropdownMeasurementTypeExpanded) }
    }

    internal fun onDateSelected(date: Date) {
        _state.update {
            it.copy(
                selectedDate = date.toMonthAndYear(),
                dropdownDateExpanded = false
            )
        }.also {
            viewModelScope.launch {
                _state.update { it.copy(measurements = getMeasurements()) }
            }
        }
    }

    internal fun onDateDropdownSelected() {
        _state.update { it.copy(dropdownDateExpanded = !it.dropdownDateExpanded) }
    }

    private suspend fun getMeasurements(): List<Measurement> {
        return getMeasurementsUseCase(
            _state.value.selectedDate,
            _state.value.selectedMeasurementType
        )
    }
}
