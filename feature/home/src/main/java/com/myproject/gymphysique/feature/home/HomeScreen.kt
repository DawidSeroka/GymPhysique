package com.myproject.gymphysique.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.myproject.gymphysique.feature.measurements.R

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    HomeScreen(onClick = onClick)
}

@Composable
fun HomeScreen(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "HomeScreen")
        Button(onClick = onClick) {
            Text(stringResource(id = R.string.home))
        }
    }
}