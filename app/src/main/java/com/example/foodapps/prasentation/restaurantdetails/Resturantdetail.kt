package com.example.foodapps.prasentation.restaurantdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodapps.prasentation.restaurantdetails.component.RestaurantDetailsSubPart
import com.example.foodapps.prasentation.restaurantdetails.component.RestaurantListHeaderDetails
import com.example.foodapps.ui.theme.FoodAppsTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RestaurantDetail() {
    val mutableResult = MutableStateFlow("")
    val sharedResult = MutableSharedFlow<String>()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        RestaurantListHeaderDetails(onBackClick = { /* Handle back button click */ })
        RestaurantDetailsSubPart()

    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun RestaurantItemPreview() {
    FoodAppsTheme {
        RestaurantDetail()
    }
}