package com.example.foodapps.prasentation.restaurantdetails

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodapps.prasentation.restaurantdetails.component.RestaurantDetailsSubPart
import com.example.foodapps.prasentation.restaurantdetails.component.RestaurantListHeaderDetails


@Composable
fun RestaurantDetail(
    viewModel: ResturantDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onOrderClick: () -> Unit,
) {
    val restaurantUiState by viewModel.uiStateRestrauntDetails.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            // TopAppBar content here
            if (restaurantUiState is ResturantDetailsState.Success) {
                val details = (restaurantUiState as ResturantDetailsState.Success)
                RestaurantListHeaderDetails(
                    onBackClick = onBackClick,
                    restaurantName = details.restaurantDetails.item_name,
                    restaurantImage = details.restaurantDetails.item_image_url,
                    delivery_type = details.restaurantDetails.delivery_type,
                    is_like = details.restaurantDetails.is_like,
                    onToggleFavorite = {
                        // Call ViewModel function
                        Log.d("TAG", "RestaurantDetail::::: ${details.restaurantDetails.id}")
//                        viewModel.toggleRestaurantLikeStatus(menuItemId = details.restaurantDetails.id, currentIsLiked = details.restaurantDetails.is_like)
//                        viewModel.addFavorite(menuItemId = details.restaurantDetails.id)
//                         If you have separate add and remove functions in ViewModel, you'd choose based on successState.restaurantDetails.is_like:
                         if (details.restaurantDetails.is_like) {
//                             viewModel.removeFavorite(successState.restaurantDetails.id)
                         } else {
                             viewModel.addToFavorites(menuItemId = details.restaurantDetails.id)
                         }

                    }
                )
            }
        }
    ) { innerPadding ->
        // Scaffold content here
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isLargeScreen = this.maxWidth > 600.dp
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = if (isLargeScreen) 48.dp else 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (val resturantDetailsState = restaurantUiState) {
                    is ResturantDetailsState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    is ResturantDetailsState.Error -> {
                        Text(
                            text = "No categories found.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    is ResturantDetailsState.Success -> {
                        if (restaurantUiState is ResturantDetailsState.Success) {
                            val details = (restaurantUiState as ResturantDetailsState.Success)
                            RestaurantDetailsSubPart(
                                modifier = Modifier.fillMaxWidth(),
                                restaurantDetails = details.restaurantDetails
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SeeMoreButton(onClickOrder = { onOrderClick() })
            }
        }
    }
}

@Composable
fun SeeMoreButton(onClickOrder: () -> Unit) {
    Button(
        onClick = onClickOrder,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Text("Order", color = MaterialTheme.colorScheme.onPrimary)
    }
}

