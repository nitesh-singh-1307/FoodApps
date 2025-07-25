package com.example.foodapps.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.foodapps.R
import com.example.foodapps.ui.theme.AppTheme

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    isInvalidEmailFormat: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onEmailChanged: (newValue: String) -> Unit,
    testTag: String = "email"
) {
    TextInput(
        modifier = modifier,
        text = email,
        label = stringResource(id = R.string.enter_email),
        hint = stringResource(id = R.string.email_hint),
        onTextChanged = onEmailChanged,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        error = {
            if (isInvalidEmailFormat) {
                Text(
                    text = stringResource(id = R.string.error_bad_email_format),
                    color = AppTheme.colorScheme.error
                )
            }
        },
        testTag = testTag
    )
}
