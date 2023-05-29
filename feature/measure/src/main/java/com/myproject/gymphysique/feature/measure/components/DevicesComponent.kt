package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.common.animations.bounceClick
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.ConnectionState
import com.myproject.gymphysique.feature.measure.AdvertisementWrapper
import com.myproject.gymphysique.feature.measurements.R

@Composable
internal fun DevicesComponent(
    modifier: Modifier = Modifier,
    advertisements: List<AdvertisementWrapper>,
    scanTime: Int?,
    onScanClicked: () -> Unit,
    onConnectDeviceClicked: (Advertisement) -> Unit,
    onDisconnectClicked: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bluetooth_animation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

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
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Devices",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(Dimens.halfMargin))
        scanTime?.let {
            LottieAnimation(
                modifier = Modifier.weight(1f),
                composition = composition,
                progress = { progress }
            )
        } ?: run {
            if (advertisements.isEmpty()) {
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
                        text = "No available devices",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Black
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(advertisements) { advertisement ->
                        val isLastAdvertisement =
                            advertisements.indexOf(advertisement) == advertisements.lastIndex
                        AdvertisementItem(
                            modifier = Modifier.bounceClick(),
                            advertisementWrapper = advertisement,
                            onConnectDeviceClick = onConnectDeviceClicked
                        )
                        if (!isLastAdvertisement) {
                            Divider(
                                modifier = Modifier
                                    .height(1.dp)
                                    .padding(horizontal = Dimens.halfMargin)
                            )
                        }
                    }
                }
            }
        }
        Buttons(
            advertisements = advertisements,
            onDisconnectClicked = onDisconnectClicked,
            onScanClicked = onScanClicked,
            scanTime = scanTime
        )
    }
}

@Composable
private fun Buttons(
    advertisements: List<AdvertisementWrapper>,
    onDisconnectClicked: () -> Unit,
    onScanClicked: () -> Unit,
    scanTime: Int?
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (advertisements.any { it.connectionState == ConnectionState.CONNECTED }) {
            Button(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onDisconnectClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.85f)
                )
            ) {
                Text(text = "Disconnect")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onScanClicked,
                enabled = scanTime == null
            ) {
                scanTime?.let { Text(text = "Scanning ${it}s") } ?: Text(text = "Scan devices")
            }
        } else {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = onScanClicked,
                enabled = scanTime == null
            ) {
                scanTime?.let { Text(text = "Scanning ${it}s") } ?: Text(text = "Scan devices")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDevicesScanAdvertising() {
    GymPhysiqueTheme {
        DevicesComponent(
            advertisements = emptyList(),
            scanTime = null,
            onScanClicked = { },
            onConnectDeviceClicked = {},
            onDisconnectClicked = {}
        )
    }
}
