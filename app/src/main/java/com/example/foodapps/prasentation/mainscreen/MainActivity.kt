package com.example.foodapps.prasentation.mainscreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import com.example.foodapps.prasentation.filters.components.FilterScreen
import com.example.foodapps.prasentation.foodListScreen.components.FoodsMenuScreen
import com.example.foodapps.prasentation.nvgraph.NavGraph
import com.example.foodapps.prasentation.restaurantlistscreen.component.RestaurantListScreen
import com.example.foodapps.ui.theme.FoodAppsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appEntryPoint: AppEntryUseCases
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        installSplashScreen().apply {
//            setKeepOnScreenCondition(condition = { viewModel.welcomeScreen.value })
//        }

        setContent {
//            FoodAppsTheme {
//                lifecycleScope.launch {
//                    appEntryPoint.readAppEntry().collect{
//                        Log.d("TEST::::", it.toString() )
//                    }
//                }
//                NavGraph(startDestination = viewModel.startDestination.value , appEntryPoint)
//            }
//            FoodsMenuScreen()
//            FilterScreen(onSearch = {}, onReset = {})
            RestaurantListScreen()
        }

    }
}
