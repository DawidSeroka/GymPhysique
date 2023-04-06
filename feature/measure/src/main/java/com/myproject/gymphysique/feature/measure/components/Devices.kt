package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myproject.gymphysique.core.designsystem.icon.GPIcons
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
internal fun Devices() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Devices", style = MaterialTheme.typography.titleLarge)
            Icon(imageVector = GPIcons.Search, contentDescription = GPIcons.Search.name)
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.halfMargin),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            if (false)
                LazyColumn() {
                    items(4) {
                        Text(text = "$it")
                    }
                }
            else
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.margin),
                    text = "No available device",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
        }
    }
}

@Preview
@Composable
private fun DevicesPreview() {
    GymPhysiqueTheme {
        Devices()
    }
}