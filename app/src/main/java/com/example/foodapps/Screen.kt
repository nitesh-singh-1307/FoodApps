package com.example.foodapps

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Discover : Screen("discover")
    object Filter : Screen("filter")
    object Orders : Screen("orders")
    object Restaurant : Screen("restaurant")
    object RestaurantDetails : Screen("restaurantDetails")
    object FoodMenuItemScreen : Screen("foodMenuItemScreen")
    object Favorites : Screen("favorites")
    object Notifications : Screen("notifications")
}

// BottomNavigationItem data class
data class BottomNavigationItem(
    val icon: Int,
    val route: String
)