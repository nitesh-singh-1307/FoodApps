package com.example.foodapps.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.foodapps.prasentation.signupscreen.components.SignUpScreenDimens.SmallSpacing

val placeHolderColor = Color(0xFF98A2CC)
val textLabelColor = Color(0xFF250A82)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    // Add password visibility state and toggle if needed
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {}
) {
    // Define the reusable color configuration
    val customTextFieldColors = outlinedTextFieldColors(
        focusedBorderColor =  textLabelColor,
        unfocusedBorderColor = placeHolderColor
        // Add any other shared color customizations here
    )
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge, // Or bodySmall
            color = textLabelColor, // Slightly muted color
            modifier = Modifier.padding(bottom = SmallSpacing)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = placeHolderColor
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword /*&& !isPasswordVisible*/) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            colors = customTextFieldColors,
            shape = MaterialTheme.shapes.medium,
            trailingIcon = { // Example for password visibility toggle
                if (isPassword) {
                    val image =
                        if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            }
        )
    }
}