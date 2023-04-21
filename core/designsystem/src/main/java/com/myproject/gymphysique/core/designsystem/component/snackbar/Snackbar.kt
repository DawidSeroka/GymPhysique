package com.myproject.gymphysique.core.designsystem.component.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
fun SnackbarRounded(
    message: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.screenPadding),
        colors = CardDefaults.cardColors(
            containerColor = SnackbarDefaults.color
        ),
        shape = RoundedCornerShape(Dimens.margin),

        ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
                .padding(Dimens.screenPadding),
            text = message,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Preview(name = "Light mode")
@Preview(name = "Dark mode")
@Composable
fun previewSnackbarPendingMeasurements() {
    GymPhysiqueTheme() {
        SnackbarRounded(
            message = "Error: Test 123",
            onClick = {}
        )
    }
}