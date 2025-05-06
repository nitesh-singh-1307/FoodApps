package com.example.foodapps.prasentation.foodListScreen.components

import androidx.lifecycle.ViewModel
import com.example.foodapps.data.remote.model.MenuState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel : ViewModel() {
    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()

    fun selectCategory(category: String) {
        // Update state logic
    }
}