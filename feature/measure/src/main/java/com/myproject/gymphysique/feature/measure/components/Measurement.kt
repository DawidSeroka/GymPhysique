package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
internal fun Measurement() {

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
                if (true)
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.margin),
                            text = "No available measurements",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                else
                    items(4) {
                        Text(text = "$it")
                    }
                item {
                    Box(
                        modifier = Modifier
                            .padding(bottom = Dimens.halfMargin)
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {},
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        ) {
                            Text(text = "Add measurement")
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
        Measurement()
    }
}