package com.example.foodapps.prasentation.nvgraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.common.LoadingScreen
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import com.example.foodapps.prasentation.bottomNavigator.BottomNavigate
import com.example.foodapps.prasentation.homescreen.components.HomeScreen
import com.example.foodapps.prasentation.signinscreen.SignInViewModel
import com.example.foodapps.prasentation.signinscreen.components.SignInScreen
import com.example.foodapps.prasentation.welcomescreen.WelcomeScreenViewModel
import com.example.foodapps.prasentation.welcomescreen.components.WelcomeScreen

@Composable
fun NavGraph(
    startDestination: String,
    readAppEntry: AppEntryUseCases,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.LoadingScreen.route
    ) {
        composable(Route.LoadingScreen.route) {
            LoadingScreen(readAppEntry,
                onLoadingComplete = { isFirstLaunch ->
                    Log.d("isFirstLaunch::", isFirstLaunch.toString())
                    if (!isFirstLaunch) {
                        navController.navigate(Route.WelcomeScreen.route) {
                            popUpTo(Route.LoadingScreen.route) { inclusive = true }
                        }
                    }else{
                        navController.navigate(Route.SignInScreen.route) {
                            popUpTo(Route.LoadingScreen.route) { inclusive = true }
                        }
                    }
                }
            )
        }


        composable(route = Route.WelcomeScreen.route) {
            val viewModel: WelcomeScreenViewModel = hiltViewModel()
            WelcomeScreen(
                onEvent = viewModel::onEvent,
                onLogin = { navController.navigate(Route.SignInScreen.route) })
//            navController.navigate(Route.SignInScreen.route) {
//                popUpTo(Route.WelcomeScreen.route) { inclusive = true }
//            }
        }

        composable(route = Route.SignInScreen.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            SignInScreen(viewModel,navController)

        }

        composable(route = Route.HomeScreen.route) {
//            val viewModel: SignInViewModel = hiltViewModel()
//            NavigationDrawer(navController)
            HomeScreen(navController)
        }
    }
}