package com.example.foodapps.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
fun CircularButtonWithFavoriteIcon(
    isLiked: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    Log.d("CircularButtonWithFavoriteIcon::::", "isLiked: $isLiked")
    if (isLiked) {
        Box(
            modifier = modifier
                .clickable { onFavoriteClick() }
                .background(Color.Blue, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favorite_icon),
                modifier = iconModifier,
                contentDescription = "Add to favorites",
                tint = Color.White
            )
        }
    } else {
        Box(
            modifier = modifier
                .clickable { onFavoriteClick() }
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.favorite_icon),
                modifier = iconModifier,
                contentDescription = "Add to favorites",
                tint = Color.White
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun CircularButtonWithFavoriteIconPreview() {
    FoodAppsTheme {
        CircularButtonWithFavoriteIcon()
    }
}