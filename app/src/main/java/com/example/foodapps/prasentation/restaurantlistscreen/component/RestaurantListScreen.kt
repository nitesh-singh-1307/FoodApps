package com.example.foodapps.prasentation.restaurantlistscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapps.R
import com.example.foodapps.common.CircularButtonWithFavoriteIcon
import com.example.foodapps.data.remote.model.RestaurantList
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme

// Constants
private val HeaderHeight = 200.dp
private val HorizontalPadding = 16.dp
private val BackButtonPadding = PaddingValues(all = 8.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantListScreen() {
    val restaurants = rememberRestaurants()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        RestaurantListHeader(onBackClick = { /* Handle back button click */ })
        RestaurantListItem(restaurants = restaurants)

    }
}

@Composable
fun RestaurantListHeader(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(HeaderHeight) // Custom size
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        HeaderBackground()
        HeaderText()
        BackButton(onBackClick)
    }
}

@Composable
fun BackButton(onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart)
                .padding(BackButtonPadding)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_button_desc),
                tint = Color.White
            )
        }
    }

}

@Composable
fun HeaderText() {
    Text(
        text = stringResource(R.string.iranian_food_title),
        style = MaterialTheme.typography.titleMedium,
        color = Color.White,
        modifier = Modifier.padding(horizontal = HorizontalPadding)
    )

}

@Composable
fun HeaderBackground() {
    Image(
        painter = painterResource(R.drawable.foodimg),
        contentDescription = "Header background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

// Restaurant List Component
@Composable
fun RestaurantListItem(
    restaurants: List<RestaurantList>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(restaurants) { restaurant ->
            RestaurantListItemWithDetails(restaurant = restaurant)
        }
    }
}

// List Item Component
@Composable
fun RestaurantListItemWithDetails(restaurant: RestaurantList) {
    var isLiked by remember { mutableStateOf(false) } // Local state
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side - Dish Image
            Image(
                painter = painterResource(R.drawable.foodimg), // Replace with your image resource
                contentDescription = "Dish image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(6.dp),
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Add",
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        fontSize = 8.sp,
                        text = "4.5",
                        style = AppTheme.typography.labelSmall,
                    )
                }
                Text(
                    text = restaurant.name,
                    fontSize = 14.sp,
                    style = AppTheme.typography.labelNormal,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        painter = painterResource(id = R.drawable.ic_map),
                        contentDescription = "Add",
                    )
                    Spacer(modifier = Modifier.width(1.dp))
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur.",
                        style = AppTheme.typography.labelSmall,
                        fontSize = 8.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color.Blue, shape = RoundedCornerShape(14.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Free Delivery",
                        color = Color.White,
                        fontSize = 8.sp,
                        style = AppTheme.typography.labelSmall,
                        modifier = Modifier.wrapContentSize()
                    )
                }

            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            CircularButtonWithFavoriteIcon(
                isLiked = isLiked,
                onFavoriteClick = { isLiked = !isLiked },
                modifier = Modifier.size(30.dp),
                iconModifier = Modifier.size(14.dp)
            )
        }
    }

}

// Data Source
@Composable
fun rememberRestaurants(): List<RestaurantList> = remember {
    listOf(
        RestaurantList(1, "Iranian food"),
        RestaurantList(2, "Shalimar"),
        RestaurantList(3, "Asian Petal"),
        RestaurantList(4, "Moka Club"),
        RestaurantList(5, "Banni Coffee Club")
    )
}

// Preview
@Preview(showBackground = true)
@Composable
fun RestaurantItemPreview() {
    FoodAppsTheme {
        RestaurantListScreen()
    }
}