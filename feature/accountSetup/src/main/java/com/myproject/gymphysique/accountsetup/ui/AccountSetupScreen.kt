package com.myproject.gymphysique.accountsetup.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.accountsetup.AccountSetupState
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupActions
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupViewModel
import com.myproject.gymphysique.core.components.ProfileSetupComponent
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysqiue.core.domain.util.ValidateResult
import kotlinx.coroutines.launch
import timber.log.Timber

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
            onValidateResultReset = viewModel::onValidateResultReset,
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
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val saveUserDataResult = uiState.saveUserDataResult
    val validateResult = uiState.validateResult

    LaunchedEffect(key1 = saveUserDataResult) {
        saveUserDataResult?.let {
            snackbarHostState.showSnackbar(saveUserDataResult.message.asString(context))
            screenActions.onSaveUserDataResultReset()
        }
    }

    LaunchedEffect(key1 = validateResult) {
        if (validateResult is ValidateResult.Error) {
            snackbarHostState.showSnackbar(message = validateResult.message)
            screenActions.onValidateResultReset()
        }
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> screenActions.onImageUriSelected(uri) }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) { Snackbar(it) } }
    ) { paddingValues ->
        ProfileSetupComponent(
            modifier = Modifier.fillMaxSize().padding(
                all = paddingValues.calculateBottomPadding() - paddingValues.calculateBottomPadding()
            ).also {
                Timber.d("PaddingValues = $paddingValues")
            },
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

@Preview(showBackground = true)
@Composable
private fun AccountScreenPreview() {
    GymPhysiqueTheme {
        AccountScreen(
            uiState = AccountSetupState(
                firstName = TextFieldValue("Dawid")
            ),
            screenActions = AccountSetupActions({}, {}, {}, {}, {}, {}, {}, {}, {}, {})
        )
    }
}
