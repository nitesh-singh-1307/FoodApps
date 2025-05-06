package com.example.foodapps.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
fun NextCommanButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    buttonColor: Color = Color.Blue,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 16.dp)
           ,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = textColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Text(text, color = textColor , style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun NextCommanButtonPreview() {
        FoodAppsTheme {
            NextCommanButton(onClick = { /* Handle see more button click */ },
                text = "Process order",
                modifier = Modifier.fillMaxWidth()
            )
        }
}