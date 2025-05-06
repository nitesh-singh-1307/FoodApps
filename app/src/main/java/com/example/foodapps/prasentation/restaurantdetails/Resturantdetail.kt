package com.example.foodapps.prasentation.restaurantdetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodapps.prasentation.restaurantdetails.component.RestaurantDetailsSubPart
import com.example.foodapps.prasentation.restaurantdetails.component.RestaurantListHeaderDetails
import com.example.foodapps.ui.theme.FoodAppsTheme


@Composable
fun RestaurantDetail(
    navController: NavController,
    onBackClick: () -> Unit,
    onSeeMoreClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            // TopAppBar content here
            RestaurantListHeaderDetails(onBackClick =  onBackClick )
        }
    ) { innerPadding ->
        // Scaffold content here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RestaurantDetailsSubPart(modifier = Modifier)
            Spacer(modifier = Modifier.height(16.dp)) // Add some space between the components
            SeeMoreButton(onClick = { onSeeMoreClick() })

        }
    }
}

@Composable
fun SeeMoreButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Text("See More", color = MaterialTheme.colorScheme.onPrimary)
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun RestaurantItemPreview() {
    FoodAppsTheme {
        RestaurantDetail(
            navController = NavController(LocalContext.current),
            onBackClick = { /* Handle back click in preview */ },
            onSeeMoreClick = { /* Handle see more click in preview */ }
        )
    }
}