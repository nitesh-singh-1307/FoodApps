package com.example.foodapps.prasentation.nvgraph

sealed class Route(
    val route: String
) {
    data object WelcomeScreen: Route("welcome_screen")
    data object SignUpScreen: Route("signup_screen")
    data object SignInScreen: Route("sign_screen")
    data object HomeScreen: Route("home_screen")
    data object AppStartNavigation: Route("app_start_navigation")
    data object SignInNavigation: Route("sign_in_navigation")
    data object LoadingScreen: Route("loading")
}