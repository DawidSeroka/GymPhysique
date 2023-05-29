package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.common.supportedServices.SupportedService
import com.myproject.gymphysique.core.designsystem.icon.GPIcons
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.model.ConnectionState
import com.myproject.gymphysique.feature.measure.AdvertisementWrapper

@Composable
internal fun AdvertisementItem(
    modifier: Modifier = Modifier,
    advertisementWrapper: AdvertisementWrapper,
    onConnectDeviceClick: (Advertisement) -> Unit
) {
    val deviceName = advertisementWrapper.advertisement.peripheralName ?: (advertisementWrapper.advertisement.name ?: "Unnamed")
    val deviceType = SupportedService.values().first { supportedService ->
        advertisementWrapper.advertisement.uuids.any { serviceUuid ->
            supportedService.uuid == serviceUuid.toString()
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.margin)
            .clickable { onConnectDeviceClick(advertisementWrapper.advertisement) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = deviceName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = deviceType.fullName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            when (advertisementWrapper.connectionState) {
                ConnectionState.CONNECTED -> Icon(
                    imageVector = GPIcons.Circle,
                    contentDescription = GPIcons.Circle.name,
                    tint = Color.Green
                )
                ConnectionState.DISCONNECTED -> Icon(
                    imageVector = GPIcons.Circle,
                    contentDescription = GPIcons.Circle.name,
                    tint = Color.Red
                )
                else -> CircularProgressIndicator()
            }
        }
    }
}
