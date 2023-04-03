package com.myproject.gymphysique.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    SettingsScreen(onClick = onClick)
}

@Composable
internal fun SettingsScreen(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "SettingsScreen")
        Button(onClick = onClick) {
            Text(text = "Go to Home")
        }
    }
}