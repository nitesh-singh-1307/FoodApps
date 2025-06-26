package com.example.foodapps.prasentation.bottomNavigator.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapps.R
import com.example.foodapps.Screen
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme
import com.google.firebase.messaging.NotificationParams.isNotification

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    notificationBadgeCount: Int
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = AppTheme.colorScheme.white,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onItemClick(index) },
                icon = {

                    Box(modifier = Modifier.size(28.dp)) {
                        Icon(
                            painter = painterResource(id = bottomNavigationItem.icon),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        if (bottomNavigationItem.route == Screen.Notifications.route && notificationBadgeCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color.Red, CircleShape)
                                    .align(Alignment.TopEnd)
                            ) {
                                Text(
                                    text = notificationBadgeCount.toString(),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colorScheme.loginTitle,
                    unselectedIconColor = colorResource(id = R.color.black),
                )
            )
        }
    }
}

data class BottomNavigationItem(
    val icon: Int,
    val route: String
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    FoodAppsTheme {
        BottomNavigationBar(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.home_icon, route = Screen.Discover.route),
                BottomNavigationItem(icon = R.drawable.list_icon, route = Screen.Orders.route),
                BottomNavigationItem(
                    icon = R.drawable.favorite_icon,
                    route = Screen.Favorites.route
                ),
                BottomNavigationItem(
                    icon = R.drawable.notification_icon,
                    route = Screen.Notifications.route
                ),
            ), selectedItem = 0, onItemClick = {} , 0)
    }
}