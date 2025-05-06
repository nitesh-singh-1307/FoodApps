package com.example.foodapps.prasentation.homescreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.prasentation.homescreen.SearchBarBorderColor
import com.example.foodapps.prasentation.homescreen.SearchBarIconColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Search"
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val customTextFieldColors = outlinedTextFieldColors(
        focusedBorderColor = SearchBarBorderColor, // Keep border consistent or highlight
        unfocusedBorderColor = SearchBarBorderColor,
        cursorColor = SearchBarIconColor, // Match cursor to icon color
        focusedLeadingIconColor = SearchBarIconColor,
        unfocusedLeadingIconColor = SearchBarIconColor,
        focusedTrailingIconColor = SearchBarIconColor,
        unfocusedTrailingIconColor = SearchBarIconColor,
        focusedPlaceholderColor = SearchBarIconColor.copy(alpha = 0.7f),
        unfocusedPlaceholderColor = SearchBarIconColor.copy(alpha = 0.7f),
        // Add any other shared color customizations here
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        placeholder = { Text(placeholderText) },
        leadingIcon = { Icon(Icons.Filled.Search, "Search") },
        trailingIcon = { FilterButton(onClick = onFilterClick) },
        shape = MaterialTheme.shapes.small,
        colors = customTextFieldColors,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch()
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}

@Composable
private fun FilterButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.Tune,
            contentDescription = stringResource(R.string.filter_options)
        )
    }
}