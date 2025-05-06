package com.example.foodapps.prasentation.mainscreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.foodapps.AppRoot
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.example.foodapps.ui.theme.FoodAppsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appEntryPoint: AppEntryUseCases
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodAppsTheme{
                AppRoot()

            }
        }
    }
}
