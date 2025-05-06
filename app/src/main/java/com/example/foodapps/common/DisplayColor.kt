package com.example.foodapps.common
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

val loginTitle = Color(0xFF3F51B5)

@Composable
fun DisplayColor() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(loginTitle)
    )
}

@Preview
@Composable
fun PreviewDisplayColor() {
    DisplayColor()
}