package com.myproject.gymphysique.accountsetup.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.accountsetup.AccountSetupState
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupActions
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupViewModel
import com.myproject.gymphysique.core.common.Gender
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
internal fun AccountSetupRoute(
    modifier: Modifier = Modifier,
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
            onGenderSelected = viewModel::onGenderSelected,
            onHeightChange = viewModel::onHeightChange,
            onAgeChange = viewModel::onAgeChange,
            onSaveUserClick = viewModel::onSaveUserClick,
            onDropdownSelected = viewModel::onDropdownSelected
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    uiState: AccountSetupState,
    screenActions: AccountSetupActions
) {
    var text by remember {
        mutableStateOf(TextFieldValue())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Looks like you're new here", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(Dimens.quarterMargin))
        Text(
            text = "Create account now",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(Dimens.dialogMargin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "First name") },
            isError = false,
            value = uiState.firstName,
            onValueChange = { screenActions.onFirstNameChange(it) }
        )
        Spacer(modifier = Modifier.height(Dimens.dialogMargin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Surname") },
            isError = false,
            value = uiState.surname,
            onValueChange = { screenActions.onSurnameChange(it) }
        )
        Spacer(modifier = Modifier.height(Dimens.dialogMargin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Height") },
            isError = false,
            value = uiState.height,
            onValueChange = { screenActions.onHeightChange(it) }
        )
        Spacer(modifier = Modifier.height(Dimens.dialogMargin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Age") },
            isError = false,
            value = uiState.age,
            onValueChange = { screenActions.onAgeChange(it) }
        )
        Spacer(modifier = Modifier.height(Dimens.dialogMargin))
        SelectSexComponent(
            genders = Gender.values().toList(),
            expanded = uiState.expanded,
            selectedGender = uiState.gender,
            onGenderSelected = { screenActions.onGenderSelected(it) },
            onDismissRequest = { screenActions.onDropdownSelected() },
            onExpandedChange = { screenActions.onDropdownSelected() }
        )
        Spacer(modifier = Modifier.height(Dimens.dialogMargin))
        Button(onClick = { screenActions.onSaveUserClick() }) {
            Text(text = "Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectSexComponent(
    genders: List<Gender>,
    expanded: Boolean,
    selectedGender: Gender?,
    onGenderSelected: (Gender) -> Unit,
    onDismissRequest: () -> Unit,
    onExpandedChange: () -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { onExpandedChange() }
    ) {
        OutlinedTextField(
            value = selectedGender?.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Gender") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
            genders.forEach {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onGenderSelected(it) }
                        .padding(Dimens.halfMargin),
                    text = it.name
                )
                if (genders.last() != it) Divider()
            }
        }
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
            screenActions = AccountSetupActions(
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            )
        )
    }
}
