package com.example.foodapps

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.prasentation.bottomNavigator.components.DiscoverScreen
import com.example.foodapps.prasentation.filters.FilterScreen
import com.example.foodapps.prasentation.foodListScreen.FoodsMenuScreen
import com.example.foodapps.prasentation.restaurantdetails.RestaurantDetail
import com.example.foodapps.prasentation.signinscreen.SignInScreen
import com.example.foodapps.prasentation.signupscreen.SignUpScreen
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.foodapps.prasentation.orderScreen.FoodOrderSummaryScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: @Composable Modifier = Modifier
) {
    Log.d("AppNavHost", "startDestination::::: $startDestination")
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Login.route) {
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Discover.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Screen.Signup.route) }
            )
        }


        composable(route = Screen.Discover.route) {
            DiscoverScreen(
                onNavigateToResturantDetails = { restaurantId ->
                    navController.navigate(Screen.RestaurantDetails.createRoute(restaurantId))
                },
                onNavigateToFilter = { navController.navigate(Screen.Filter.route) },
//                onNavigateToOrders = { navController.navigate(Screen.Orders.route) },
//                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) }
            )
        }



        composable(route = Screen.RestaurantDetails.route,
            arguments = listOf(navArgument("id") {
            type = NavType.StringType
        })) {
            RestaurantDetail(
                onOrderClick = { navController.navigate(Screen.Orders.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.FoodMenuItemScreen.route) {
            // Add your FoodMenuItemScreen content here
            // For example, you can create a new Composable for the food menu item screen
            FoodsMenuScreen(
                onBackClick = { navController.popBackStack() },
                onSeeMoreClick = {}
            )
        }
        composable(route = Screen.Orders.route) {
            FoodOrderSummaryScreen()
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
//        composable(route = Screen.Notifications.route) { NotificationsScreen() }

    }
}

