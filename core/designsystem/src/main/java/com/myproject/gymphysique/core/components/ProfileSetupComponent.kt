package com.myproject.gymphysique.core.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myproject.gymphysique.core.designsystem.R
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.Gender

@Composable
fun ProfileSetupComponent(
    modifier: Modifier = Modifier,
    firstname: String,
    surname: String,
    age: String,
    height: String,
    gender: Gender,
    imageUri: Uri?,
    firstnameError: Boolean,
    surnameError: Boolean,
    ageError: Boolean,
    heightError: Boolean,
    expanded: Boolean,
    onFirstnameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onGenderSelected: (Gender) -> Unit,
    onDropdownSelected: () -> Unit,
    onUploadPhotoSelected: () -> Unit,
    onSaveSelected: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserPhotoComponent(imageUri, onUploadPhotoSelected)
        UserDataComponent(
            firstname,
            onFirstnameChange,
            firstnameError,
            surname,
            onSurnameChange,
            surnameError,
            height,
            onHeightChange,
            heightError,
            age,
            onAgeChange,
            ageError,
            expanded,
            gender,
            onGenderSelected,
            onDropdownSelected,
            onSaveSelected
        )
    }
}

@Suppress("LongMethod")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun UserDataComponent(
    firstname: String,
    onFirstnameChange: (String) -> Unit,
    firstnameError: Boolean,
    surname: String,
    onSurnameChange: (String) -> Unit,
    surnameError: Boolean,
    height: String,
    onHeightChange: (String) -> Unit,
    heightError: Boolean,
    age: String,
    onAgeChange: (String) -> Unit,
    ageError: Boolean,
    expanded: Boolean,
    gender: Gender,
    onGenderSelected: (Gender) -> Unit,
    onDropdownSelected: () -> Unit,
    onSaveSelected: () -> Unit
) {
    Column(
        modifier = Modifier.padding(Dimens.screenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstname,
            onValueChange = onFirstnameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            supportingText = {
                if (firstnameError) {
                    Text(text = stringResource(R.string.firstname_error))
                }
            },
            trailingIcon = {
                if (firstnameError) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = "IconError")
                }
            },
            label = { Text(text = stringResource(id = R.string.firstname)) },
            isError = firstnameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(Dimens.margin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = surname,
            onValueChange = onSurnameChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            supportingText = {
                if (surnameError) {
                    Text(text = stringResource(R.string.surname_error))
                }
            },
            trailingIcon = {
                if (surnameError) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = "IconError")
                }
            },
            label = { Text(text = stringResource(id = R.string.surname)) },
            isError = surnameError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(Dimens.margin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = height,
            onValueChange = onHeightChange,
            label = { Text(text = stringResource(id = R.string.height)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            supportingText = {
                if (heightError) {
                    Text(text = stringResource(R.string.height_error))
                }
            },
            trailingIcon = {
                if (heightError) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = "IconError")
                }
            },
            isError = heightError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(Dimens.margin))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = age,
            onValueChange = onAgeChange,
            label = { Text(text = stringResource(id = R.string.age)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            supportingText = {
                if (ageError) {
                    Text(text = stringResource(R.string.age_error))
                }
            },
            trailingIcon = {
                if (ageError) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = "IconError")
                }
            },
            isError = ageError,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(Dimens.margin))
        SelectGenderComponent(
            genders = Gender.values().toList(),
            expanded = expanded,
            selectedGender = gender,
            onGenderSelected = onGenderSelected,
            onDismissRequest = onDropdownSelected,
            onExpandedChange = onDropdownSelected
        )
        Spacer(modifier = Modifier.height(Dimens.margin))
        Button(onClick = onSaveSelected) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
private fun UserPhotoComponent(imageUri: Uri?, onUploadPhotoSelected: () -> Unit) {
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
            model = imageUri,
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
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            onClick = onUploadPhotoSelected
        ) {
            Text(text = stringResource(R.string.upload_new_photo))
        }
    }
}

@Preview
@Composable
fun ProfileSetupComponentPreview() {
    GymPhysiqueTheme {
        ProfileSetupComponent(
            modifier = Modifier,
            firstname = "",
            surname = "",
            age = "",
            height = "",
            gender = Gender.OTHER,
            imageUri = null,
            firstnameError = false,
            surnameError = false,
            ageError = false,
            heightError = false,
            expanded = false,
            {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}
