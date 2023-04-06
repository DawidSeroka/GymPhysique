package com.myproject.gymphysique.feature.measure

import android.content.res.Configuration
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.feature.measure.components.Devices
import com.myproject.gymphysique.feature.measure.components.Measurement
import com.myproject.gymphysique.feature.measurements.R

@Composable
internal fun MeasureRoute(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MeasureScreen(onClick = onClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasureScreen(onClick: () -> Unit) {
    val context = LocalContext.current
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimens.screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Devices()
            Spacer(modifier = Modifier.padding(Dimens.margin))
            Measurement()
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
        MeasureScreen {

        }
    }
}