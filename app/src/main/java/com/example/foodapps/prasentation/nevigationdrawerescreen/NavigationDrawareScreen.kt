package com.example.foodapps.prasentation.nevigationdrawerescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodapps.R
import com.example.foodapps.prasentation.homescreen.components.HomeScreen
import com.example.foodapps.prasentation.nvgraph.Route
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme
import kotlinx.coroutines.launch


// Drawer Content
@Composable
fun DrawerContent(onItemSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)

    ) {
        // User profile Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.2f)) // Optional: Subtle background
                .padding(start = 16.dp, end = 16.dp, top = 56.dp, bottom = 56.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Image
                Image(
                    painter = painterResource(id = R.drawable.user_profile), // Replace with your image resource
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(60.dp) // Set the size of the image
                        .clip(CircleShape), // Clip the image into a circle
                    contentScale = ContentScale.Crop, // Crop the image to fit within the circle
                colorFilter = ColorFilter.tint(AppTheme.colorScheme.loginTitle)
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Username
                Column {
                    Text(
                        text = "John Doe", // Replace with dynamic username
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.colorScheme.loginTitle
                    )
                    Text(
                        text = "View Profile",
                        fontSize = 14.sp,
                        color = AppTheme.colorScheme.loginTitle,
                        modifier = Modifier.clickable {
                            onItemSelected("profile")
                        }
                    )
                }
            }
        }

        // Divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                route = "Home",
                onItemSelected
            )
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.Person,
                route = "profile",
                onItemSelected
            )
            NavigationItem(
                title = "Settings",
                icon = Icons.Default.Settings,
                route = "settings",
                onItemSelected
            )
        }
    }
}

@Composable
fun NavigationItem(
    title: String,
    icon: ImageVector,
    route: String,
    onItemSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemSelected(route) }
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = AppTheme.typography.labelLarge)
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    FoodAppsTheme {
        val navController = rememberNavController()
//        NavigationDrawer(navController)
    }
}