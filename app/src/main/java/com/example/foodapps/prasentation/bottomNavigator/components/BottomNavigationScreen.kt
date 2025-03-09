package com.example.foodapps.prasentation.bottomNavigator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.R
import com.example.foodapps.prasentation.homescreen.components.HomeScreen
import com.example.foodapps.prasentation.nevigationdrawerescreen.DrawerContent
import com.example.foodapps.prasentation.nvgraph.Route
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationScreen(navController: NavHostController) {
//    val navController =  rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val bottomNavigationItems = listOf( BottomNavigationItem(icon = R.drawable.home_icon),
        BottomNavigationItem(icon = R.drawable.list_icon),
        BottomNavigationItem(icon = R.drawable.favorite_icon),
        BottomNavigationItem(icon = R.drawable.notification_icon),)
    val backStackState = navController.currentBackStackEntryAsState()

    selectedItem = when (backStackState.value?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.FoodListScreen.route -> 1
        Route.FavoriteScreen.route -> 2
        Route.NotificationScreen.route -> 3
        else -> 0
    }
    // Hide the bottom navigation bar when the current screen is not the home screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState.value?.destination?.route == Route.HomeScreen.route ||
                backStackState.value?.destination?.route == Route.HomeScreen.route ||
                backStackState.value?.destination?.route == Route.HomeScreen.route
    }
    // Calculate 50% of the screen width
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true // Avoid duplicate entries in back stack
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        // Scaffold for the main content, top app bar, and bottom navigation
        Scaffold(
            Modifier.background(AppTheme.colorScheme.background),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(painter  = painterResource(R.drawable.menu_icon),
                                contentDescription = null, modifier = Modifier.size(24.dp))

                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )
                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.FoodListScreen.route
                            )
                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.FavoriteScreen.route
                            )
                            3 -> navigateToTab(
                                navController = navController,
                                route = Route.NotificationScreen.route
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Main content with padding to avoid overlapping with bottom bar
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
            ) {
                HomeScreen()
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
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

@Composable
@Preview(showBackground = true)
private fun BottomPreview() {
    FoodAppsTheme {
        val navController = rememberNavController()
        BottomNavigationScreen(navController)
    }
}