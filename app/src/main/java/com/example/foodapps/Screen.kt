package com.example.foodapps

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Discover : Screen("discover")
    object HomeScreen : Screen("homescreen")
    object Filter : Screen("filter")
    object Orders : Screen("orders")
    object Restaurant : Screen("restaurant")
    object RestaurantDetails : Screen("restaurantDetails/{id}"){
        fun createRoute(restaurantId: String) = "restaurantDetails/$restaurantId"
    }
    object FoodMenuItemScreen : Screen("foodMenuItemScreen")
    object Favorites : Screen("favorites")
    object Notifications : Screen("notifications")
}

