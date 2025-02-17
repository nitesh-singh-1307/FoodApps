package com.example.foodapps.prasentation.bottomNavigator.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
    fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
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
                    Icon(
                        painter = painterResource(id = bottomNavigationItem.icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colorScheme.loginTitle,
                    unselectedIconColor = colorResource(id = R.color.black),
                ),
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    FoodAppsTheme {
    BottomNavigationBar(items = listOf(
            BottomNavigationItem(icon = R.drawable.home_icon),
            BottomNavigationItem(icon = R.drawable.list_icon),
            BottomNavigationItem(icon = R.drawable.favorite_icon),
            BottomNavigationItem(icon = R.drawable.notification_icon),
        ), selectedItem = 0, onItemClick = {})
    }
}