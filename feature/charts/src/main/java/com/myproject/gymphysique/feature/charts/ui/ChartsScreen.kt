package com.myproject.gymphysique.feature.charts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
internal fun ChartsRoute() {
    ChartsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChartsScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Text(text = "Charts")
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Charts2")
        }
    }
}

@Preview
@Composable
private fun ChartsPreview() {
    GymPhysiqueTheme() {
        ChartsScreen()
    }
}
