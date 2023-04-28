package com.myproject.gymphysique.feature.charts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.feature.charts.ChartsState
import com.myproject.gymphysique.feature.charts.viewModel.ChartsViewModel
import timber.log.Timber

@Composable
internal fun ChartsRoute(
    modifier: Modifier = Modifier,
    viewModel: ChartsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle().also {
        Timber.d("test ${it.value.weightMeasurements}, \n" +
                "${it.value.softLeanMassMeasurements} \n" +
                "$it")
    }

    ChartsScreen(
        uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartsScreen(
    uiState: ChartsState
) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                LazyRow{
                    items(uiState.weightMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.bmiMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.basalMetabolismMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.bodyFatMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.bodyWaterMassMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.boneMassMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.fatFreeMassMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.idealWeightMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.muscleMassMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.musclePercentageMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.softLeanMassMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
            item {
                LazyRow{
                    items(uiState.visceralFatMeasurements){measurement ->
                        MeasurementItem(measurement = measurement)
                    }
                }
            }
        }
    }
}

@Composable
private fun MeasurementItem(measurement: Measurement) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.margin),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = measurement.measurementType.name)
            Row {
                Text(text = measurement.measurementResult.toString())
                Text(text = measurement.measurementType.unit.toString())
            }
        }
    }
}

@Preview
@Composable
private fun ChartsPreview() {
    GymPhysiqueTheme() {
        ChartsScreen(ChartsState())
    }
}
