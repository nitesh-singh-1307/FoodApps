package com.example.foodapps.prasentation.bottomNavigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.R
import com.example.foodapps.prasentation.bottomNavigator.components.BottomNavigationBar
import com.example.foodapps.prasentation.bottomNavigator.components.BottomNavigationItem
import com.example.foodapps.prasentation.homescreen.components.HomeScreen
import com.example.foodapps.prasentation.nvgraph.Route

@Composable
fun BottomNavigate(){
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.home_icon),
            BottomNavigationItem(icon = R.drawable.list_icon),
            BottomNavigationItem(icon = R.drawable.favorite_icon),
            BottomNavigationItem(icon = R.drawable.notification_icon)
        )
    }
    val navController =  rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    selectedItem = when(backStackState.value?.destination?.route){
        Route.HomeScreen.route -> 0
        Route.HomeScreen.route -> 1
        Route.HomeScreen.route -> 2
        Route.HomeScreen.route -> 3
        else -> 0
    }
    // Hide the bottom navigation bar when the current screen is not the home screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState.value?.destination?.route == Route.HomeScreen.route ||
                backStackState.value?.destination?.route == Route.HomeScreen.route ||
                backStackState.value?.destination?.route == Route.HomeScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
        if(isBottomBarVisible){
            BottomNavigationBar(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when(index){
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(route = Route.HomeScreen.route){ backStackEntry ->
//                val navController = rememberNavController()
                HomeScreen(navController)
            }

        }
    }

}
private fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}
