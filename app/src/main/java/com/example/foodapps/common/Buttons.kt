package com.example.foodapps.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        modifier = modifier.padding(horizontal = AppTheme.size.small),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colorScheme.primary,
            contentColor = AppTheme.colorScheme.onPrimary
        ),
        shape = AppTheme.shape.button
    ) {
        Text(
            text = label,
            style = AppTheme.typography.labelLarge
        )
    }
}
