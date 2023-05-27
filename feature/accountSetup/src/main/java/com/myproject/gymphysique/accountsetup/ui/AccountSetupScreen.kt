package com.myproject.gymphysique.accountsetup.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.accountsetup.AccountSetupState
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupActions
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupViewModel
import com.myproject.gymphysique.core.components.ProfileSetupComponent
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import kotlinx.coroutines.launch

@Composable
internal fun AccountSetupRoute(
    viewModel: AccountSetupViewModel = hiltViewModel(),
    onNavigateToGpApp: () -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navigateToGpApp = uiState.navigateToGpApp

    LaunchedEffect(key1 = navigateToGpApp) {
        if (navigateToGpApp) {
            onNavigateToGpApp()
            viewModel.resetNavigateToGpApp()
        }
    }

    AccountScreen(
        uiState = uiState,
        screenActions = AccountSetupActions(
            onFirstNameChange = viewModel::onFirstNameChange,
            onSurnameChange = viewModel::onSurnameChange,
            onHeightChange = viewModel::onHeightChange,
            onAgeChange = viewModel::onAgeChange,
            onGenderSelected = viewModel::onGenderSelected,
            onSaveSelected = viewModel::onSaveSelected,
            onSaveUserDataResultReset = viewModel::onSaveUserDataResultReset,
            onDropdownSelected = viewModel::onDropdownSelected,
            onImageUriSelected = viewModel::onImageUriSelected
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    uiState: AccountSetupState,
    screenActions: AccountSetupActions
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val saveUserDataResult = uiState.saveUserDataResult

    LaunchedEffect(key1 = saveUserDataResult) {
        saveUserDataResult?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(saveUserDataResult.message.asString(context))
                screenActions.onSaveUserDataResultReset()
            }
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> screenActions.onImageUriSelected(uri) }
    )

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) { Snackbar(it) } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTitle()
            ProfileSetupComponent(
                firstname = uiState.firstName,
                surname = uiState.surname,
                age = uiState.age,
                height = uiState.height,
                gender = uiState.gender,
                imageUri = uiState.selectedImageUri,
                firstnameError = uiState.firstnameError,
                surnameError = uiState.surnameError,
                ageError = uiState.ageError,
                heightError = uiState.heightError,
                expanded = uiState.expanded,
                onFirstnameChange = screenActions.onFirstNameChange,
                onSurnameChange = screenActions.onSurnameChange,
                onAgeChange = screenActions.onAgeChange,
                onHeightChange = screenActions.onHeightChange,
                onGenderSelected = screenActions.onGenderSelected,
                onDropdownSelected = screenActions.onDropdownSelected,
                onUploadPhotoSelected = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                    )
                },
                onSaveSelected = screenActions.onSaveSelected
            )
        }
    }
}

@Composable
private fun ScreenTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = Dimens.halfMargin,
                    bottomEnd = Dimens.halfMargin
                )
            )
            .background(MaterialTheme.colorScheme.onPrimaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Account Setup",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    GymPhysiqueTheme {
        AccountScreen(
            uiState = AccountSetupState(
                firstName = "Dawid"
            ),
            screenActions = AccountSetupActions({}, {}, {}, {}, {}, {}, {}, {}, {})
        )
    }
}
