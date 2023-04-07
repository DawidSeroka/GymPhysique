package com.myproject.gymphysique.feature.measure.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.common.SupportedServices.SupportedService
import com.myproject.gymphysique.core.common.toUUID
import com.myproject.gymphysique.core.designsystem.theme.Dimens

@Composable
fun AdvertisementItem(advertisement: Advertisement) {
    val deviceName = advertisement.peripheralName ?: (advertisement.name ?: "Unnamed")
    val deviceType = SupportedService.values().first {supportedService ->
        advertisement.uuids.any {serviceUuid ->
            supportedService.uuid == serviceUuid.toString()
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(Dimens.halfMargin),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = deviceName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = deviceType.fullName,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}