package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.Measurement

@Composable
internal fun Measurement(
    measurements: List<Measurement>,
    measureState: Boolean,
    onStartMeasureClick: () -> Unit,
    onSaveClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Measurements", style = MaterialTheme.typography.titleLarge,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.halfMargin),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            LazyColumn {
                items(measurements){

                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onStartMeasureClick,
                        ) {
                            Text(text = "Start measure")
                        }
                        Button(onClick = onSaveClick) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun MeasurementPreview() {
    GymPhysiqueTheme {
        Measurement(
        )
    }
}