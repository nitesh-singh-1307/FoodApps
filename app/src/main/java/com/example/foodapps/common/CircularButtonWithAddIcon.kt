package com.example.foodapps.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
fun CircularButtonWithIcon() {
    Box(
        modifier = Modifier.size(30.dp)
            .background(Color.Blue, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_input_add),
            contentDescription = "Add",
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodMenuItemPreview() {
    FoodAppsTheme {
        CircularButtonWithIcon()
    }
}