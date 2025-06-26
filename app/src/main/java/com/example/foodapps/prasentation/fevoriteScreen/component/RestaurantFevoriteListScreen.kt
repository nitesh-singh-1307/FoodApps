package com.example.foodapps.prasentation.fevoriteScreen.component

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodapps.R
import com.example.foodapps.common.CircularButtonWithFavoriteIcon
import com.example.foodapps.data.remote.model.RestaurantLikeDetails
import com.example.foodapps.prasentation.fevoriteScreen.LikedItemsUiState
import com.example.foodapps.prasentation.fevoriteScreen.ResturantLikeViewModel
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme

// Constants
private val HeaderHeight = 200.dp
private val HorizontalPadding = 16.dp
private val BackButtonPadding = PaddingValues(all = 8.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantFevoriteListScreen(
    viewModel: ResturantLikeViewModel = hiltViewModel(),
) {
//    var restaurants = rememberRestaurants()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("TAG", "RestaurantFevoriteListScreen:::: ${uiState}")

            when (val state = uiState) {
                is LikedItemsUiState.Loading -> {
                    // To center the CircularProgressIndicator, it needs to be placed
                    // within a Composable that allows for alignment, like Box or Column.
                    // Since the Scaffold already provides padding, we can use a Box
                    // that fills the available space.
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LikedItemsUiState.Error -> {
                    // To center the error message and retry button, they need to be placed
                    // within a Composable that allows for alignment, like Box or Column.
                    // Since the Scaffold already provides padding, we can use a Box
                    // that fills the available space and centers its content.
                    Log.d("TAG","Error::::: ${state.message}")
                    ErrorView(message = state.message, onRetry = { viewModel.loadLikedItems() })
                }

                is LikedItemsUiState.Success -> {
                    if (state.likedItems.isEmpty()) {
                        // To center the Text, it needs to be placed
                        // within a Composable that allows for alignment, like Box or Column.
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "You haven't liked any items yet!",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                    } else {
                        Scaffold(
                            topBar = {
                                RestaurantListHeader(
                                    restaurants = state.likedItems
                                )
                            }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    RestaurantListItem(restaurants = state.likedItems)
                                }
                            }
                        }

                    }
                }
            }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(BackButtonPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error: $message", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}


@Composable
fun RestaurantListHeader(
    restaurants: List<RestaurantLikeDetails>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(HeaderHeight) // Custom size
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
//        Image(
//            painter = painterResource(R.drawable.foodimg),
//            contentDescription = "Header background",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(restaurants[1].item_image_url)
                .crossfade(true)
                .placeholder(R.drawable.foodimg) // Add this line for placeholder
                .build(),
            contentDescription = "Header background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // You can use the restaurants list here if needed for the header
        HeaderText(title = "${restaurants[1].item_name} (${restaurants.size})")
    }
}


@Composable
fun HeaderText(title: String) {
    Text(
        text = title,
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
    restaurants: List<RestaurantLikeDetails>
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        items(restaurants) { restaurant ->
            RestaurantListItemWithDetails(restaurant = restaurant)
        }
    }
}

// List Item Component
@Composable
fun RestaurantListItemWithDetails(restaurant: RestaurantLikeDetails) {
//    var isLiked by remember { mutableStateOf(false) } // Local state
    Log.d("TAG", "RestaurantListItemWithDetails::::: ${restaurant.item_image_url}")
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

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(restaurant.item_image_url)
                    .crossfade(true)
                    .placeholder(R.drawable.foodimg) // Add this line for placeholder
                    .build(),
                contentDescription = restaurant.item_name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(11.dp),
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Add",
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        fontSize = 12.sp,
                        text = "4.5",
                        style = AppTheme.typography.labelSmall,
                    )
                }
                Text(
                    text = restaurant.item_name,
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
                        text = restaurant.description,
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
                        text = restaurant.delivery_type,
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
                isLiked = restaurant.is_like,
                onFavoriteClick = { },
                modifier = Modifier.size(30.dp),
                iconModifier = Modifier.size(14.dp)
            )
        }
    }

}


// Preview
@Preview(showBackground = true)
@Composable
fun RestaurantItemPreview() {
    FoodAppsTheme {
//        RestaurantFevoriteListScreen()
    }
}