package com.myproject.gymphysique.feature.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.feature.measurements.R
import timber.log.Timber

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}) {
                Text(text = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "HomeScreen")
            Button(
                modifier = Modifier.weight(1f),
                onClick = onClick
            ) {
                Text(stringResource(id = R.string.home))
            }
            Text(text = "Test")
        }


    }

}

@Preview(name = "Light mode", showBackground = true, showSystemUi = true)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomePreview() {
    GymPhysiqueTheme() {
        HomeScreen {

        }
    }
}