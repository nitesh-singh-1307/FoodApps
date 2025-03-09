package com.example.foodapps.prasentation.nvgraph

sealed class Route(
    val route: String
) {
    data object WelcomeScreen: Route("welcome_screen")
    data object SignInScreen: Route("sign_screen")
    data object HomeScreen: Route("home_screen")
    data object LoadingScreen: Route("loading")
    data object FavoriteScreen: Route("favorite_screen")
    data object NotificationScreen: Route("notification_screen")
    data object FoodListScreen: Route("food_list_screen")
    data object ProfileScreen: Route("profile_screen")
    data object SettingScreen: Route("setting_screen")
    data object BottomNavigationScreen: Route("bottom_screen")
}