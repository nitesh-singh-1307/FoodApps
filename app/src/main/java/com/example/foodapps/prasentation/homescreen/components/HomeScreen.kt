package com.example.foodapps.prasentation.homescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.R
import com.example.foodapps.data.remote.model.CategoryItem
import com.example.foodapps.data.remote.model.Restaurant
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            // Header Selection
            Text(
                text = stringResource(id = R.string.home_screen_title),
                style = MaterialTheme.typography.titleLarge,
                color = AppTheme.colorScheme.loginTitle,
                modifier = Modifier.padding(top = 5.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Search Bar
            SearchBar(modifier = Modifier.padding(bottom = 24.dp))
            // Categories section
            CategoriesSection()
            // Restaurant List
            RestaurantList()
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
            .background(Color.White)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)), // Add a border
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Icon",
            tint = Color(0xFF6200EA) // Adjust the color to match your design
        )
        Spacer(modifier = Modifier.width(4.dp))
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.str_search),
                    color = AppTheme.colorScheme.loginTitle,
                    style = AppTheme.typography.labelSmall,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
        // Filter Image
        Image(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = R.drawable.filter_icon),
            contentDescription = "filter Image",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = AppTheme.colorScheme.loginTitle)
        )
        Spacer(modifier = Modifier.width(8.dp))

    }
}

@Composable
fun CategoriesSection() {
    var categories by remember {
        mutableStateOf(
            listOf(
                CategoryItem("Show all", R.drawable.user_profile),
                CategoryItem("Vegan", R.drawable.user_profile),
                CategoryItem("Pasta", R.drawable.user_profile),
                CategoryItem("Asian", R.drawable.user_profile),
                CategoryItem("Burg", R.drawable.user_profile),
                CategoryItem("Free delivery", R.drawable.user_profile)
            )
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Categories title
        Text(
            text = stringResource(id = R.string.str_category),
            style = MaterialTheme.typography.labelMedium,
            color = AppTheme.colorScheme.loginTitle,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Text(
            text = stringResource(id = R.string.str_show_all),
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.colorScheme.loginTitle,
        )
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.NavigateNext,
            contentDescription = "move",
            tint = Color(0xFF6200EA), // Adjust the color to match your design
        )
    }
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { categoryIndex ->
            val category = categories[categoryIndex]
            CategoryItems(category = category,
                category.isSelected,
                onClick = {
                    // Update the selected state of all items
                    categories = categories.map {
                        if (it.name == category.name) {
                            it.copy(isSelected = true) // Select this item
                        } else {
                            it.copy(isSelected = false) // Deselect others
                        }
                    }
                    println("Clicked on ${category.name}")
                }
            )
        }
    }

}

@Composable
fun CategoryItems(
    category: CategoryItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .clickable(onClick = onClick) // Handle click action
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp), // Rounded corners
        elevation = CardDefaults.cardElevation( // Use CardElevation
            defaultElevation = if (isSelected) 8.dp else 4.dp,
            pressedElevation = 2.dp, // Optional: Elevation when pressed
            hoveredElevation = 6.dp, // Optional: Elevation when hovered
            focusedElevation = 6.dp // Optional: Elevation when focused
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AppTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circular Image
            Image(
                painter = painterResource(id = category.image),
                contentDescription = category.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp)) // Space between image and text
// Text with selection highlight
            Text(
                text = category.name,
                style = AppTheme.typography.labelNormal,
                color = if (isSelected) Color.Blue else Color.Black, // Change text color when selected
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
        }
    }
}


@Composable
fun RestaurantList() {
    val restaurants = listOf(
        Restaurant("Ronald Club", 4.5f, true),
        Restaurant("Garage Bar", 0.0f, true)
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 columns
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        items(restaurants.size) { restaurantsIndex ->
            RestaurantItem(restaurant = restaurants[restaurantsIndex])
        }
    }
}

@Composable
fun RestaurantItem(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),

            ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.foodimg),
                contentDescription = restaurant.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(color = Color.Blue, shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "₹100 OFF",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.wrapContentSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = restaurant.name,
                    style = AppTheme.typography.labelLarge,
                    color = AppTheme.colorScheme.white,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFF00C853), shape = RoundedCornerShape(8.dp))
                        .wrapContentSize()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "4.2",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating Star",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    FoodAppsTheme {
        val navController = rememberNavController()
        HomeScreen()
    }
}
