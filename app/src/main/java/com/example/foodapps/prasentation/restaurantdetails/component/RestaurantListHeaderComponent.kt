package com.example.foodapps.prasentation.restaurantdetails.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodapps.R
import com.example.foodapps.common.CircularButtonWithFavoriteIcon
import com.example.foodapps.ui.theme.FoodAppsTheme

// Constants
private val HeaderHeight = 250.dp
private val HorizontalPadding = 16.dp
private val BackButtonPadding = PaddingValues(all = 8.dp)


@Composable
fun RestaurantListHeaderDetails(
    onBackClick: () -> Unit,
    restaurantName: String = "",
    restaurantImage: String = "",
    delivery_type: String = "",
    is_like: Boolean,
    onToggleFavorite: () -> Unit, // Add this callback
    modifier: Modifier = Modifier
) {

    var boxHeight by remember { mutableStateOf(0.dp) }
    val offsetY by remember(boxHeight) {
        derivedStateOf { boxHeight * 0.5f } // 20% of box height
    }
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(HeaderHeight) // Custom size
            .background(MaterialTheme.colorScheme.background)
            .onSizeChanged { layoutCoordinates ->
                boxHeight = with(density) { layoutCoordinates.height.toDp() }
            },
        contentAlignment = Alignment.Center
    ) {
        HeaderBackgroundDetails(restaurantImage = restaurantImage)
        BackButtonDetails(onBackClick = onBackClick)
        HeaderTextDetails(restaurantName = restaurantName, delivery_type = delivery_type)
        FavoriteButton(
            offsetY = offsetY,
            isLiked = is_like,
            onFavoriteClick = { onToggleFavorite() }
        )
    }
}

@Composable
fun FavoriteButton(
    offsetY: Dp,
    isLiked: Boolean,
    onFavoriteClick: () -> Unit
) {
    var isLikeds by remember { mutableStateOf(false) } // Local state
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp)
            .offset(y = offsetY),
        contentAlignment = Alignment.BottomEnd,
    ) {
        CircularButtonWithFavoriteIcon(
            isLiked = isLiked,
            onFavoriteClick = { onFavoriteClick() } ,
            modifier = Modifier.size(55.dp),
            iconModifier = Modifier.size(32.dp)
        )
    }

}

@Composable
fun HeaderTextDetails(restaurantName: String = "", delivery_type: String = "") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.Bottom // Align spaced group to bottom
        )
    ) {
        if (!delivery_type.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .background(color = Color.Blue, shape = RoundedCornerShape(14.dp))
                    .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
                PromotionText(
                    text = delivery_type,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }

        PromotionText(
            text = restaurantName,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }

}

@Composable
private fun PromotionText(
    text: String,
    style: TextStyle,
    color: Color
) {
    Text(
        text = text,
        style = style,
        color = color,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
fun HeaderBackgroundDetails(restaurantImage: String = "") {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(restaurantImage) // The URL from Firestore
            .crossfade(true).build(),
        contentDescription = "Header background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
fun BackButtonDetails(onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(BackButtonPadding)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back_button_desc),
                tint = Color.White
            )
        }
    }

}

// Preview
@Preview(showBackground = true)
@Composable
fun RestaurantItemPreview() {
    FoodAppsTheme {
        RestaurantListHeaderDetails(
            onBackClick = { /* Handle back button click */ },
            is_like = false,
            onToggleFavorite = { /* Handle favorite toggle */ }
        )
    }
}