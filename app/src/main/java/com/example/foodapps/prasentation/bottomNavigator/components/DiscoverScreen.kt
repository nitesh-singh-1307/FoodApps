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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.R
import com.example.foodapps.Screen
import com.example.foodapps.prasentation.fevoriteScreen.FavoritesScreen
import com.example.foodapps.prasentation.homescreen.HomeScreen
import com.example.foodapps.prasentation.nevigationdrawerescreen.DrawerContent
import com.example.foodapps.prasentation.notifications.NotificationsScreen
import com.example.foodapps.prasentation.orderScreen.OrdersScreen
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavHostController,
    onNavigateToScreen: (String) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomNavigationItems = listOf(
        BottomNavigationItem(icon = R.drawable.home_icon, route = Screen.Discover.route),
        BottomNavigationItem(icon = R.drawable.list_icon, route = Screen.Orders.route),
        BottomNavigationItem(icon = R.drawable.favorite_icon, route = Screen.Favorites.route),
        BottomNavigationItem(
            icon = R.drawable.notification_icon,
            route = Screen.Notifications.route
        )
    )

    // Calculate 50% of the screen width
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.background(AppTheme.colorScheme.background),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                painter = painterResource(R.drawable.menu_icon),
                                contentDescription = "Menu",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavigationItems,
                    selectedItem = bottomNavigationItems.indexOfFirst { it.route == currentRoute },
                    onItemClick = { index ->
                        val route = bottomNavigationItems[index].route
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Main content based on current route
                when (currentRoute) {
                    Screen.Discover.route -> HomeScreen(navController = navController)
                    Screen.Orders.route -> OrdersScreen()
                    Screen.Favorites.route -> FavoritesScreen()
                    Screen.Notifications.route -> NotificationsScreen()
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun BottomPreview() {
    val navController = rememberNavController()

    FoodAppsTheme {
        DiscoverScreen(
            navController = navController,
            onNavigateToScreen = {}
        )
    }
}