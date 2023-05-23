package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.ConnectionState
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.feature.measure.AdvertisementWrapper

@Composable
internal fun MeasurementComponent(
    modifier: Modifier = Modifier,
    measurements: List<Measurement>,
    measureState: Boolean,
    onSearchMeasurementsClick: () -> Unit,
    onStopMeasureClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.halfMargin))
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(Dimens.halfMargin),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Measurements",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        if (measurements.isNotEmpty())
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = Dimens.halfMargin)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = Color.Black
                    )
            ) {
                items(measurements) { measurement ->
                    MeasurementItem(measurement = measurement)
                    if (measurements.last() != measurement) {
                        Divider(modifier = Modifier.padding(horizontal = Dimens.halfMargin))
                    }
                }
            }
        else
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.margin),
                    text = "No available measurements",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        Buttons(
            measurements = measurements,
            onSaveClicked = onSaveClick,
            onStartMeasureClicked = onSearchMeasurementsClick,
            onStopMeasureClick = onStopMeasureClick,
            measureState = measureState
        )
    }
}

@Composable
private fun Buttons(
    measurements: List<Measurement>,
    onSaveClicked: () -> Unit,
    onStartMeasureClicked: () -> Unit,
    onStopMeasureClick: () -> Unit,
    measureState: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (measurements.isNotEmpty() && measureState == true) {
            Button(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onStopMeasureClick,
            ) {
                Text(text = "Stop measure")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onSaveClicked,
            ) {
                Text(text = "Save")
            }
        } else if (measurements.isNotEmpty()) {
            Button(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onStartMeasureClicked,
            ) {
                Text(text = "Start measure")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onSaveClicked,
            ) {
                Text(text = "Save")
            }
        } else if (measureState == true) {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = onStopMeasureClick,
            ) {
                Text(text = "Stop measure")
            }
        } else
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = onStartMeasureClicked,
            ) {
                Text(text = "Start measure")
            }
    }
}

@Composable
fun MeasurementItem(measurement: Measurement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.screenPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = measurement.measurementType.fullName,
            style = MaterialTheme.typography.titleMedium
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${measurement.measurementResult} ")
            Text(
                text = measurement.measurementType.unit.unit,
                style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MeasurementPreview() {
    GymPhysiqueTheme {
        MeasurementComponent(
            measurements = emptyList(),
            measureState = false,
            onSearchMeasurementsClick = {},
            onStopMeasureClick = {},
            onSaveClick = {}
        )
    }
}
