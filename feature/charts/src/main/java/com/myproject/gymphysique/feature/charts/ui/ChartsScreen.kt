package com.myproject.gymphysique.feature.charts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.myproject.gymphysique.feature.charts.components.ChartComponent
import com.myproject.gymphysique.feature.charts.viewModel.ChartsViewModel

@Composable
internal fun ChartsRoute(
    modifier: Modifier = Modifier,
    viewModel: ChartsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

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
                ChartComponent(uiState.weightMeasurements)
            }
            item {
                ChartComponent(uiState.bmiMeasurements)
            }
            item {
                ChartComponent(uiState.basalMetabolismMeasurements)
            }
            item {
                ChartComponent(uiState.bodyFatMeasurements)
            }
            item {
                ChartComponent(uiState.bodyWaterMassMeasurements)
            }
            item {
                ChartComponent(uiState.boneMassMeasurements)
            }
            item {
                ChartComponent(uiState.fatFreeMassMeasurements)
            }
            item {
                ChartComponent(uiState.idealWeightMeasurements)
            }
            item {
                ChartComponent(uiState.muscleMassMeasurements)
            }
            item {
                ChartComponent(uiState.musclePercentageMeasurements)
            }
            item {
                ChartComponent(uiState.softLeanMassMeasurements)
            }
            item {
                ChartComponent(uiState.visceralFatMeasurements)
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
