package com.myproject.gymphysique.core.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.core.model.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupComponent(
    modifier: Modifier = Modifier,
    firstname: TextFieldValue,
    surname: TextFieldValue,
    age: TextFieldValue,
    height: TextFieldValue,
    gender: Gender,
    imageUri: Uri?,
    firstnameError: Boolean,
    surnameError: Boolean,
    ageError: Boolean,
    heightError: Boolean,
    expanded: Boolean,
    onFirstnameChange: (TextFieldValue) -> Unit,
    onSurnameChange: (TextFieldValue) -> Unit,
    onAgeChange: (TextFieldValue) -> Unit,
    onHeightChange: (TextFieldValue) -> Unit,
    onGenderSelected: (Gender) -> Unit,
    onDropdownSelected: () -> Unit,
    onUploadPhotoSelected: () -> Unit,
    onSaveSelected: () -> Unit
) {
    LazyColumn(
        modifier = modifier
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
                    value = firstname,
                    onValueChange = { onFirstnameChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "Firstname") },
                    isError = firstnameError,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(Dimens.margin))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = surname,
                    onValueChange = { onSurnameChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "Surname") },
                    isError = surnameError,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(Dimens.margin))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = height,
                    onValueChange = { onHeightChange(it) },
                    label = { Text(text = "Height") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    isError = heightError,
                    maxLines = 1
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = age,
                    onValueChange = { onAgeChange(it) },
                    label = { Text(text = "Age") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
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
                    Text(text = "Save")
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileSetupComponentPreview() {
    GymPhysiqueTheme {
        ProfileSetupComponent(
            modifier = Modifier,
            firstname = TextFieldValue(),
            surname = TextFieldValue(),
            age = TextFieldValue(),
            height = TextFieldValue(),
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
