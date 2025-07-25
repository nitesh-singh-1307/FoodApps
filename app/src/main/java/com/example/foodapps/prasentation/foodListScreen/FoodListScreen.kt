package com.example.foodapps.prasentation.foodListScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodapps.R
import com.example.foodapps.common.CircularButtonWithIcon
import com.example.foodapps.data.remote.model.MenuCategory
import com.example.foodapps.prasentation.foodListScreen.components.FoodMenuItem
import com.example.foodapps.prasentation.foodListScreen.components.MenuViewModel
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.Dimens.iconMedium
import com.example.foodapps.ui.theme.FoodAppsTheme


@Composable
fun FoodsMenuScreen(
    onBackClick: () -> Unit,
    onSeeMoreClick: () -> Unit,
    viewModel: MenuViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val menuState by viewModel.menuState.collectAsState()

    val menuCategory = listOf("Starters", "Appetizers", "Main Course", "Dessert")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val categories = mapOf(
        "Starters" to listOf(
            MenuCategory(
                "Dish name",
                "Lorem ipsum dolor sit amet, consectetur.",
                14.90,
                R.drawable.foodimg
            ),
            MenuCategory(
                "Dish name",
                "Lorem ipsum dolor sit amet, consectetur.",
                17.80,
                R.drawable.foodimg
            )
        ),
        "Appetizers" to listOf(
            MenuCategory(
                "Dish name",
                "Lorem ipsum dolor sit amet, consectetur.",
                21.50,
                R.drawable.foodimg
            ),
            MenuCategory(
                "Dish name",
                "Lorem ipsum dolor sit amet, consectetur.",
                12.70,
                R.drawable.foodimg
            )
        ),
        "Main Course" to listOf(
            MenuCategory("Steak", "Grilled sirloin steak", 24.99, R.drawable.foodimg),
            MenuCategory("Pasta", "Homemade fettuccine", 18.99, R.drawable.foodimg)
        ),
        "Dessert" to listOf(
            MenuCategory("Cake", "Chocolate layer cake", 7.99, R.drawable.foodimg),
            MenuCategory("Ice Cream", "Vanilla bean ice cream", 5.99, R.drawable.foodimg)
        )
        // Add other categories similarly
    )

    Scaffold { innerPadding->

        Box(modifier = Modifier.padding(innerPadding)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp).clickable { onBackClick() },
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = "filter Image"
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Screen Title
            Text(
                text = "Menu",
                style = AppTheme.typography.titleLarge,
                fontSize = 24.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Category Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                itemsIndexed(menuCategory){ index, category ->
                    val isSelected = selectedTabIndex == index
                    val tabColor = if (isSelected) Color.Blue else Color.White
                    val textColor = if (tabColor.luminance() > 0.5) Color.Black else Color.White

                    Box(
                      modifier = Modifier.fillMaxWidth()
                          .clip(shape = RoundedCornerShape(12.dp))
                  ){
                      Tab(
                          selected = isSelected,
                          onClick = { selectedTabIndex = index },
                          Modifier.background(tabColor).height(38.dp),
                          text = {
                              Text(
                                  text = category,
                                  color = textColor,
                                  style = AppTheme.typography.labelSmall
                              )
                          },
                          selectedContentColor = tabColor,
                          unselectedContentColor = tabColor
                      )
                  }
                    if (index < menuCategory.lastIndex) Spacer(modifier = Modifier.width(6.dp))
                }
            }
            // Food Menu Items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                items(categories[menuCategory[selectedTabIndex]] ?: emptyList()) { dish ->
                    FoodMenuItem(item = dish,
                                modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.09f)
                    )
                }
            }

        }
    }
    }

}




@Preview(showBackground = true)
@Composable
fun FoodMenuItemPreview() {
    // Provide dummy implementations for onBackClick and onSeeMoreClick
    FoodAppsTheme {
        FoodsMenuScreen(
            onBackClick = { /* Dummy implementation */ },
            onSeeMoreClick = { /* Dummy implementation */ }
        )
    }
}

