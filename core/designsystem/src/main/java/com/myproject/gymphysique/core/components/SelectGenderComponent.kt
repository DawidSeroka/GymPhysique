package com.myproject.gymphysique.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.myproject.gymphysique.core.designsystem.R
import com.myproject.gymphysique.core.designsystem.theme.Dimens
import com.myproject.gymphysique.core.model.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectGenderComponent(
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
            label = { Text(stringResource(id = R.string.gender)) },
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
