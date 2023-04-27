package com.myproject.gymphysique.core.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
fun PermissionRationaleDialog(
    onDialogClose: () -> Unit,
    permissionRequest: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.padding(horizontal = 12.dp),
        onDismissRequest = onDialogClose,
        title = {
            Text(text = "Permission Required!")
        },
        text = {
            Text("This permission is required. Please grant the permission on the next screen.")
        },
        confirmButton = {
            Button(onClick = permissionRequest) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            Button(onClick = onDialogClose) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewPermissionRationaleDialog() {
    GymPhysiqueTheme() {
        PermissionRationaleDialog(
            onDialogClose = {},
            permissionRequest = {}
        )
    }
}
