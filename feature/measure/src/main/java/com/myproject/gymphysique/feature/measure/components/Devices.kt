package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.common.animations.bounceClick
import com.myproject.gymphysique.core.designsystem.icon.GPIcons
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.utils.adaptiveHeight
import com.myproject.gymphysique.feature.measure.AdvertisingStatus
import com.myproject.gymphysique.feature.measure.PeripheralState

@Composable
internal fun Devices(
    advertisingStatus: AdvertisingStatus,
    advertisements: List<Pair<PeripheralState, Advertisement>>,
    scanTime: Int?,
    onSearchDeviceClick: () -> Unit,
    onConnectDeviceClick: (Advertisement) -> Unit
) {
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
            when (advertisingStatus) {
                AdvertisingStatus.ADVERTISING -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "$scanTime")
                        Spacer(modifier = Modifier.width(2.dp))
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    }
                }
                AdvertisingStatus.STOPPED -> Icon(
                    modifier = Modifier.clickable {
                        onSearchDeviceClick()
                    },
                    imageVector = GPIcons.Search, contentDescription = GPIcons.Search.name
                )
            }

        }
        Spacer(modifier = Modifier.height(Dimens.halfMargin))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .adaptiveHeight(advertisements.size),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            LazyColumn {
                if (advertisements.isEmpty())
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
                    items(advertisements) { advertisement ->
                        val isLastAdvertisement =
                            advertisements.indexOf(advertisement) == advertisements.lastIndex
                        AdvertisementItem(
                            modifier = Modifier.bounceClick(),
                            advertisement = advertisement,
                            onConnectDeviceClick = onConnectDeviceClick
                        )
                        if (!isLastAdvertisement)
                            Divider(
                                modifier = Modifier
                                    .height(1.dp)
                                    .padding(horizontal = Dimens.halfMargin))
                    }
            }
        }
    }
}

@Preview
@Composable
private fun DevicesPreview() {
    GymPhysiqueTheme {
        Devices(
            advertisingStatus = AdvertisingStatus.ADVERTISING,
            advertisements = emptyList(),
            scanTime = null,
            onSearchDeviceClick = {},
            onConnectDeviceClick = {}
        )
    }
}