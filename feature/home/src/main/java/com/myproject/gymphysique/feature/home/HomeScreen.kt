package com.myproject.gymphysique.feature.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.myproject.gymphysique.feature.measurements.R

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    HomeScreen(onClick = onClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClick: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "HomeScreen")
            Button(onClick = onClick) {
                Text(stringResource(id = R.string.home))
            }

        }
    }

}