package com.example.foodapps.prasentation.filters.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.foodapps.R
import com.example.foodapps.prasentation.filters.FilterSection

// OptionsSection.kt
@Composable
fun OptionsSection(
    selectedOptions: Set<String>,
    onOptionSelected: (String) -> Unit
) {
    FilterSection(title = stringResource(R.string.options)) {
        Column {
            FilterConstants.OPTIONS.forEach { option ->
                FilterOptionItem(
                    option = option,
                    selected = selectedOptions.contains(option),
                    onSelected = { onOptionSelected(option) }
                )
            }
            MoreOptionsButton()
        }
    }
}

@Composable
private fun MoreOptionsButton() {
    TextButton(
        onClick = { /* Handle more options */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.more_options),
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}