package com.example.foodapps

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.common.AuthManager.isUserLoggedIn

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val startDestination = if (isUserLoggedIn()) Screen.Discover.route else Screen.Login.route

    AppNavHost(
        navController = navController,
        startDestination = startDestination
    )
}