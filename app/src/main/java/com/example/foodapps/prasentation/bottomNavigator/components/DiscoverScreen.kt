package com.example.foodapps.prasentation.bottomNavigator.components

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.AppNavHost
import com.example.foodapps.R
import com.example.foodapps.Screen
import com.example.foodapps.common.AuthManager
import com.example.foodapps.common.AuthManager.isUserLoggedIn
import com.example.foodapps.prasentation.fevoriteScreen.component.RestaurantFevoriteListScreen
import com.example.foodapps.prasentation.homescreen.HomeScreen
import com.example.foodapps.prasentation.nevigationdrawerescreen.DrawerContent
import com.example.foodapps.prasentation.notifications.NotificationViewModel
import com.example.foodapps.prasentation.notifications.NotificationsScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    onNavigateToResturantDetails: (String) -> Unit,
    onNavigateToFilter: () -> Unit,
) {
    val notificationViewModel: NotificationViewModel = hiltViewModel(/* pass NavBackStackEntry if needed for shared scope */)
    val messages = notificationViewModel.messages.collectAsState(initial = emptyList()).value
    val messageCount = messages.size

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Log.d("DiscoverScreen", "currentRoute::::: $currentRoute")

    val bottomNavigationItems = listOf(
        BottomNavigationItem(icon = R.drawable.home_icon, route = Screen.HomeScreen.route),
        BottomNavigationItem(icon = R.drawable.list_icon, route = Screen.Orders.route),
        BottomNavigationItem(icon = R.drawable.favorite_icon, route = Screen.Favorites.route),
        BottomNavigationItem(
            icon = R.drawable.notification_icon,
            route = Screen.Notifications.route
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onItemSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
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
                    },
                    notificationBadgeCount = if (currentRoute == Screen.Notifications.route) messageCount else 0
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screen.HomeScreen.route) {
                    HomeScreen(
                        onNavigateToResturantDetails = onNavigateToResturantDetails,
                        onNavigateToFilter = onNavigateToFilter,
                    )
                }
                composable(route = Screen.Orders.route) {
//                    OrdersScreen(navController)
                }
                composable(route = Screen.Favorites.route) {
                    RestaurantFevoriteListScreen()
                }
                composable(route = Screen.Notifications.route) {
                    NotificationsScreen()
                }
            }
        }
    }
}




@Composable
@Preview(showBackground = true)
private fun BottomPreview() {
    val navController = rememberNavController()

//    FoodAppsTheme {
//        DiscoverScreen(
//
//        )
//    }
}