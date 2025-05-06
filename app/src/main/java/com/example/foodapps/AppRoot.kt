package com.example.foodapps

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.prasentation.bottomNavigator.components.DiscoverScreen
import com.example.foodapps.prasentation.fevoriteScreen.FavoritesScreen
import com.example.foodapps.prasentation.filters.FilterScreen
import com.example.foodapps.prasentation.foodListScreen.FoodsMenuScreen
import com.example.foodapps.prasentation.notifications.NotificationsScreen
import com.example.foodapps.prasentation.orderScreen.OrdersScreen
import com.example.foodapps.prasentation.restaurantdetails.RestaurantDetail
import com.example.foodapps.prasentation.restaurantlistscreen.component.RestaurantListScreen
import com.example.foodapps.prasentation.signinscreen.SignInScreen
import com.example.foodapps.prasentation.signupscreen.SignUpScreen


@Composable
fun AppRoot() {
    AppNavHost()
}
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController =  navController,
        startDestination =  Screen.Login.route,
    ){

        composable(route =  Screen.Login.route){
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Discover.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                } },
                onNavigateToSignUp = {navController.navigate(Screen.Signup.route)}
            )
        }
        composable(route = Screen.Discover.route) {
            DiscoverScreen(
                navController = navController,
                onNavigateToScreen = { route -> navController.navigate(route) }
            )
            // Add your DiscoverScreen content here
        }
        composable(route = Screen.Restaurant.route) {
            RestaurantListScreen(
                onBackClick = {navController.popBackStack()},
                onSeeMoreClick = {navController.navigate(Screen.RestaurantDetails.route)}
            )
        }


        composable(route = Screen.RestaurantDetails.route) {
            RestaurantDetail(
                navController = navController,
                onSeeMoreClick = {navController.navigate(Screen.FoodMenuItemScreen.route) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(route = Screen.FoodMenuItemScreen.route) {
            // Add your FoodMenuItemScreen content here
            // For example, you can create a new Composable for the food menu item screen
            FoodsMenuScreen(
                onBackClick = {navController.popBackStack()},
                onSeeMoreClick = {}
            )
        }
        composable(route = Screen.Signup.route) {
            SignUpScreen(
                onNavigateToMain = {
                    // Or navigate to a main screen
                     navController.navigate(Screen.Login.route) {
                         popUpTo(Screen.Signup.route) { inclusive = true }
                     }
                },
                onNavigateToUp = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
            )
        }
        composable(route = Screen.Filter.route) {
            FilterScreen(
                onSearch = { /* Handle search */ },
                onReset = { /* Handle reset */ }
            )
        }
        // Add other screen routes
        composable(route = Screen.Orders.route) { OrdersScreen() }
        composable(route = Screen.Favorites.route) { FavoritesScreen() }
        composable(route = Screen.Notifications.route) { NotificationsScreen() }

    }
}

