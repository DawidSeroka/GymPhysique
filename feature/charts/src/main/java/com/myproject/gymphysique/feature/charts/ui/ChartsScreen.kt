package com.myproject.gymphysique.feature.charts.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
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
        uiState,
        screenActions = ChartsScreenActions(
            onMeasurementDropdownSelected = viewModel::onMeasurementDropdownSelected,
            onMeasurementTypeSelected = viewModel::onMeasurementTypeSelected
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartsScreen(
    uiState: ChartsState,
    screenActions: ChartsScreenActions
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(Dimens.margin)
                .fillMaxSize()
        ) {
            ChartDropdownMenu(
                measurementTypes = MeasurementType.values().toList(),
                expanded = uiState.dropdownMeasurementExpanded,
                selectedMeasurementType = uiState.selectedMeasurementType,
                onMeasurementTypeSelected = { screenActions.onMeasurementTypeSelected(it) },
                onDismissRequest = { screenActions.onMeasurementDropdownSelected() },
                onExpandedChange = { screenActions.onMeasurementDropdownSelected() })
            ChartComponent(uiState.measurements)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartDropdownMenu(
    modifier: Modifier = Modifier,
    measurementTypes: List<MeasurementType>,
    expanded: Boolean,
    selectedMeasurementType: MeasurementType?,
    onMeasurementTypeSelected: (MeasurementType) -> Unit,
    onDismissRequest: () -> Unit,
    onExpandedChange: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { onExpandedChange() }
        ) {
            TextField(
                label = { Text("Measurement Type")},
                value = selectedMeasurementType?.fullName ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
                measurementTypes.forEach {
                    DropdownMenuItem(
                        text = { Text(it.fullName) },
                        onClick = { onMeasurementTypeSelected(it) })
                    if (measurementTypes.last() != it) Divider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChartsPreview() {
    GymPhysiqueTheme() {
        ChartsScreen(ChartsState(), ChartsScreenActions(
            {}, {}
        ))
    }
}
