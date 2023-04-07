package com.myproject.gymphysique.feature.measure.ui

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dawidraszka.composepermissionhandler.core.ExperimentalPermissionHandlerApi
import com.dawidraszka.composepermissionhandler.core.PermissionHandlerHost
import com.dawidraszka.composepermissionhandler.core.PermissionHandlerHostState
import com.dawidraszka.composepermissionhandler.core.PermissionHandlerResult
import com.dawidraszka.composepermissionhandler.utils.showAppSettingsSnackbar
import com.myproject.gymphysique.core.common.RequestBluetoothEnable
import com.myproject.gymphysique.core.common.bluetoothPermissionList
import com.myproject.gymphysique.core.designsystem.component.PermissionRationaleDialog
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.feature.measure.MeasureState
import com.myproject.gymphysique.feature.measure.components.Devices
import com.myproject.gymphysique.feature.measure.components.Measurement
import com.myproject.gymphysique.feature.measure.viewmodel.MeasureScreenActions
import com.myproject.gymphysique.feature.measure.viewmodel.MeasureViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
internal fun MeasureRoute(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    viewModel: MeasureViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    MeasureScreen(
        uiState = uiState,
        onClick = viewModel::onSearchDevicesClick,
        screenActions = MeasureScreenActions(
            onSearchDevicesClick = viewModel::onSearchDevicesClick
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionHandlerApi::class)
@Composable
private fun MeasureScreen(
    uiState: MeasureState,
    screenActions: MeasureScreenActions,
    onClick: () -> Unit
) {
    val permissionHandlerHostState =
        PermissionHandlerHostState(permissionList = bluetoothPermissionList)
    PermissionHandlerHost(
        hostState = permissionHandlerHostState,
        rationale ={ permissionRequest, dismissRequest ->
            PermissionRationaleDialog(
                onDialogClose = dismissRequest,
                permissionRequest = permissionRequest
            )
        }
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(RequestBluetoothEnable()) { result ->
        if (result) {
            screenActions.onSearchDevicesClick()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Dimens.screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Devices(
                advertisingStatus = uiState.advertisementStatus,
                advertisements = uiState.advertisements,
                onSearchDeviceClick = {
                    coroutineScope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        when (permissionHandlerHostState.handlePermissions()) {
                            PermissionHandlerResult.GRANTED -> {
                                val bluetoothManager =
                                    context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                                val bluetoothAdapter = bluetoothManager.adapter
                                if (bluetoothAdapter.isEnabled) {
                                    screenActions.onSearchDevicesClick()
                                } else {
                                    launcher.launch()
                                }
                            }
                            PermissionHandlerResult.DENIED -> {
                                snackbarHostState.showAppSettingsSnackbar(
                                    message = "App permission denied",
                                    openSettingsActionLabel = "Settings",
                                    context = context
                                )
                            }
                            PermissionHandlerResult.DENIED_NEXT_RATIONALE -> {}
                        }
                    }

                })
            Spacer(modifier = Modifier.padding(Dimens.margin))
            Measurement(uiState.advertisements)
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
        MeasureScreen(
            uiState = MeasureState(),
            onClick = {},
            screenActions = MeasureScreenActions(
                {}
            )
        )
    }
}