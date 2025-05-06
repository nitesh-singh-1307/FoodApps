package com.example.foodapps.prasentation.homescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodapps.R
import com.example.foodapps.Screen
import com.example.foodapps.data.remote.model.CategoryItem
import com.example.foodapps.data.remote.model.RestaurantFireBase
import com.example.foodapps.prasentation.homescreen.components.CategoryItem
import com.example.foodapps.prasentation.homescreen.components.CustomSearchBar
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.Dimens.dynamicHeight
import com.example.foodapps.ui.theme.FoodAppsTheme

// Define colors (Ideally, integrate these into your app's MaterialTheme.colorScheme)
val SearchBarBorderColor = Color(0xFFE0E0E0) // Light grey border
val SearchBarIconColor = Color(0xFF7A64FF) // Purple-ish icon/placeholder color
val SearchBarContainerColor = Color.White // Background of the text field

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), // Get instance via Hilt or viewModel()
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val categoryUiState by viewModel.categoryUiState.collectAsStateWithLifecycle()
    val restaurantUiState by viewModel.restaurantUiState.collectAsStateWithLifecycle()
    // Collect state lifecycle-aware
    Log.d("HomeScreen", "Category UI State: $restaurantUiState")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 20.dp, end = 20.dp)
    ) {
        var searchQuery by rememberSaveable { mutableStateOf("") }
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        SearchSection(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onFilterClick = { navController.navigate(Screen.Filter.route) },
            onSearch = { /* Handle search */ }
        )
        when (val cateState = categoryUiState) {
            is HomeUiState.Loading -> {
                // Show loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            is HomeUiState.Error -> {
                // Handle error state
                Text(
                    text = "No categories found.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is HomeUiState.Success -> {
                // Render categories
                CategoriesSection(categories = cateState.categories)
            }
        }

        when (val RestaurantState = restaurantUiState) {
            is RestaurantListUiState.Loading -> {
                // Show loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            is RestaurantListUiState.Error -> {
                // Handle error state
                Text(
                    text = "No categories found.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is RestaurantListUiState.Success -> {
                // Render categories
                RestaurantList(
                    navController = navController,
                    restaurantslist = RestaurantState.restaurants
                )
            }
        }
    }

}


@Composable
private fun HeaderSection() {
    Text(
        text = stringResource(id = R.string.home_screen_title),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun SearchSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSearch: () -> Unit
) {
    CustomSearchBar(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        onFilterClick = onFilterClick,
        onSearch = onSearch,
        modifier = Modifier.padding(bottom = 24.dp)
    )
}

@Composable
private fun CategoriesSection(categories: List<CategoryItem>) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    Log.d("CategoriesSection", "Categories:::: $categories")
    Column {
        CategoryHeader()
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.size) { index ->
                CategoryItem(
                    category = categories[index],
                    onCategorySelected = { selectedCategory = it }
                )
            }
        }
    }
}

@Composable
private fun CategoryHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.str_category),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
//        ShowAllButton()
    }
}

@Composable
private fun RestaurantList(
    navController: NavHostController,
    restaurantslist: List<RestaurantFireBase>
) {
//    val restaurants = remember { RestaurantRepository.getRestaurants() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(restaurantslist.size) { index ->
            RestaurantItem(
                restaurant = restaurantslist[index],
                navController = navController,
                modifier = Modifier.padding(bottom = AppTheme.size.normal)
            )

        }
    }
}

@Composable
fun RestaurantItem(
    restaurant: RestaurantFireBase,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(dynamicHeight)
            .clickable {
                navController.navigate(Screen.RestaurantDetails.route)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppTheme.size.small
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            RestaurantImage(restaurant_image = restaurant.image_url)
            DeliveryTag(restaurant_type = restaurant.is_available)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(AppTheme.size.normal)
            ) {
                RestaurantInfo(restaurant = restaurant)
            }
        }
    }
}

@Composable
private fun DeliveryTag(restaurant_type: Boolean) {
    Box(
        modifier = Modifier
            .padding(top = AppTheme.size.medium, start = AppTheme.size.medium)
            .background(
                color = AppTheme.colorScheme.brandColor,
                shape = MaterialTheme.shapes.small
            )
    ) {
        if (restaurant_type){
            Text(
                text = stringResource(id = R.string.free_delivery),
                style = AppTheme.typography.labelSmall,
                color = AppTheme.colorScheme.white,
                modifier = Modifier.padding(
                    horizontal = AppTheme.size.small,
                    vertical = AppTheme.size.extrasmall
                )
            )
        }

    }
}

@Composable
private fun RestaurantInfo(restaurant: RestaurantFireBase) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = restaurant.name,
            style = AppTheme.typography.labelLarge,
            color = AppTheme.colorScheme.white
        )
        Spacer(modifier = Modifier.height(AppTheme.size.extrasmall))
        RatingBar(rating = restaurant.rating ?: "0.0")
    }
}

@Composable
private fun RatingBar(rating: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = stringResource(R.string.rating_icon_desc),
            tint = AppTheme.colorScheme.white,
            modifier = Modifier.size(AppTheme.size.medium)
        )
        Spacer(modifier = Modifier.width(AppTheme.size.extrasmall))
        Text(
            text = rating,
            style = AppTheme.typography.labelLarge,
            color = AppTheme.colorScheme.white
        )
    }
}

@Composable
private fun RestaurantImage(restaurant_image: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(restaurant_image) // The URL from Firestore
            .crossfade(true).build(),
        contentDescription = stringResource(R.string.restaurant_image_desc),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
    )
}


@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    FoodAppsTheme {
        rememberNavController()
        HomeScreen(
            navController = rememberNavController(),
            modifier = Modifier
        )
    }
}
