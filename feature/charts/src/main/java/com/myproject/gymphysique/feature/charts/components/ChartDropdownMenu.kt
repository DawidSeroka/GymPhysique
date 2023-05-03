package com.myproject.gymphysique.feature.charts.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myproject.gymphysique.core.model.MeasurementType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChartDropdownMenu(
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
            OutlinedTextField(
                label = { Text("Measurement Type") },
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
fun ChartsDropdownMenuPreview() {
    ChartDropdownMenu(
        measurementTypes = emptyList(),
        expanded = false,
        selectedMeasurementType = null,
        onMeasurementTypeSelected = {},
        onDismissRequest = { /*TODO*/ }) {
    }
}