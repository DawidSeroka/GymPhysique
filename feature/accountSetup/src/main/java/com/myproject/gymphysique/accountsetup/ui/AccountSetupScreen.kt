package com.myproject.gymphysique.accountsetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupActions
import com.myproject.gymphysique.accountsetup.viewModel.AccountSetupViewModel
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme

@Composable
internal fun AccountSetupRoute(
    modifier: Modifier = Modifier,
    viewModel: AccountSetupViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    AccountScreen(
        uiState = uiState,
        screenActions = AccountSetupActions(
            onFirstNameChange = viewModel::onFirstNameChange,
            onSurnameChange = viewModel::onSurnameChange,
            onSexChange = viewModel::onSexChange,
            onHeightChange = viewModel::onHeightChange,
            onAgeChange = viewModel::onAgeChange,
            onSaveUserClick = viewModel::onSaveUserClick
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    uiState: AccountSetupState,
    screenActions: AccountSetupActions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.screenPadding)
    ) {
        Text(text = "Looks like you're new here", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(Dimens.quarterMargin))
        Text(
            text = "Create account now", style = MaterialTheme.typography.headlineMedium,
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
            label = { Text(text = "First name")},
            isError = false,
            value = TextFieldValue(uiState.firstName) ,
            onValueChange ={ screenActions.onFirstNameChange(it.text)}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Surname")},
            isError = false,
            value = TextFieldValue(uiState.surname) ,
            onValueChange ={ screenActions.onSurnameChange(it.text)}
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Height")},
            isError = false,
            value = TextFieldValue(uiState.height.toString()) ,
            onValueChange ={ screenActions.onHeightChange(it.text.toInt())}
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                capitalization = KeyboardCapitalization.Words
            ),
            label = { Text(text = "Age")},
            isError = false,
            value = TextFieldValue(uiState.height.toString()) ,
            onValueChange ={ screenActions.onAgeChange(it.text.toInt())}
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
            screenActions = AccountSetupActions(
                {}, {}, {}, {}, {}, {}
            ))
    }
}