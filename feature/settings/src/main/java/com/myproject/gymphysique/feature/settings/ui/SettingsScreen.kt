package com.myproject.gymphysique.feature.settings.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.myproject.gymphysique.core.components.SelectGenderComponent
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.feature.settings.SaveUserDataResult
import com.myproject.gymphysique.feature.settings.SettingsState
import com.myproject.gymphysique.feature.settings.viewModel.SettingsScreenActions
import com.myproject.gymphysique.feature.settings.viewModel.SettingsViewModel
import com.myproject.gymphysqiue.core.domain.util.ValidateResult
import kotlinx.coroutines.launch

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
        uiState,
        SettingsScreenActions(
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
internal fun SettingsScreen(
    uiState: SettingsState,
    screenActions: SettingsScreenActions
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val saveUserDataResult = uiState.saveUserDataResult
    val validateResult = uiState.validateResult

    LaunchedEffect(key1 = saveUserDataResult) {
        coroutineScope.launch {
            when (saveUserDataResult) {
                is SaveUserDataResult.Failure -> {
                    snackbarHostState.showSnackbar(
                        "Error: ${
                            saveUserDataResult.error.asString(
                                context
                            )
                        }"
                    )
                }

                is SaveUserDataResult.Success -> {
                    snackbarHostState.showSnackbar(
                        "Succesfully updated user: ${
                            saveUserDataResult.data.asString(
                                context
                            )
                        }"
                    )
                }

                SaveUserDataResult.Initial -> {}
            }
        }
        screenActions.onSaveUserDataResultReset()
    }

    LaunchedEffect(key1 = validateResult) {
        if (validateResult is ValidateResult.Error) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = validateResult.message)
            }
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(
                                bottomStart = Dimens.screenPadding,
                                bottomEnd = Dimens.screenPadding
                            )
                        )
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(Dimens.screenPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = uiState.selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Fit
                    )
                    Button(
                        onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                        )
                    }) {
                        Text(text = "Upload New Photo")
                    }
                }

            }
            item {
                Column(
                    modifier = Modifier.padding(Dimens.screenPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.firstName,
                        onValueChange = { screenActions.onFirstNameChange(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        label = { Text(text = "Firstname") },
                        isError = uiState.firstnameError,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(Dimens.margin))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.surname,
                        onValueChange = { screenActions.onSurnameChange(it) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        label = { Text(text = "Surname") },
                        isError = uiState.surnameError,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(Dimens.margin))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.height,
                        onValueChange = { screenActions.onHeightChange(it) },
                        label = { Text(text = "Height") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        isError = uiState.heightError,
                        maxLines = 1
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.age,
                        onValueChange = { screenActions.onAgeChange(it) },
                        label = { Text(text = "Age") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        isError = uiState.ageError,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(Dimens.margin))
                    SelectGenderComponent(
                        genders = Gender.values().toList(),
                        expanded = uiState.expanded,
                        selectedGender = uiState.gender,
                        onGenderSelected = screenActions.onGenderSelected,
                        onDismissRequest = screenActions.onDropdownSelected,
                        onExpandedChange = screenActions.onDropdownSelected
                    )
                    Spacer(modifier = Modifier.height(Dimens.margin))
                    Button(onClick = { screenActions.onSaveSelected() }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        uiState = SettingsState(),
        screenActions = SettingsScreenActions({}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    )
}
