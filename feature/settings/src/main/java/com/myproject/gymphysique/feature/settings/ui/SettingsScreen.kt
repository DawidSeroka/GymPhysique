package com.myproject.gymphysique.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.core.components.SelectGenderComponent
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.model.Gender
import com.myproject.gymphysique.feature.settings.viewModel.SettingsScreenActions
import com.myproject.gymphysique.feature.settings.viewModel.SettingsViewModel
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
            onDropdownSelected = viewModel::onDropdownSelected
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

    LaunchedEffect(key1 = saveUserDataResult){
        coroutineScope.launch {
            when(saveUserDataResult){
                is SaveUserDataResult.Failure -> {
                    snackbarHostState.showSnackbar("Error: ${saveUserDataResult.error.asString(context)}")
                }
                is SaveUserDataResult.Success -> {
                    snackbarHostState.showSnackbar("Succesfully updated user: ${saveUserDataResult.data.asString(context)}")
                }
                SaveUserDataResult.Initial -> {}
            }
        }
        screenActions.onSaveUserDataResultReset()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) { Snackbar(it) } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(Dimens.screenPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.firstName,
                onValueChange = { screenActions.onFirstNameChange(it) },
                label = { Text(text = "Firstname") }
            )
            Spacer(modifier = Modifier.height(Dimens.margin))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.surname,
                onValueChange = { screenActions.onSurnameChange(it) },
                label = { Text(text = "Surname") }
            )
            Spacer(modifier = Modifier.height(Dimens.margin))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.height,
                onValueChange = { screenActions.onHeightChange(it) },
                label = { Text(text = "Height") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.age,
                onValueChange = { screenActions.onAgeChange(it) },
                label = { Text(text = "Age") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
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

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        uiState = SettingsState(),
        screenActions = SettingsScreenActions({}, {}, {}, {}, {}, {},{},{})
    )
}
