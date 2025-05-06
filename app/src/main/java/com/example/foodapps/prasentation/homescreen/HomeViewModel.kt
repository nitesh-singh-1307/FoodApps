package com.example.foodapps.prasentation.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.data.remote.model.CategoryItem
import com.example.foodapps.data.remote.model.RestaurantFireBase
import com.example.foodapps.domain.usecases.menu_category_case.GetMenuCategoriesUseCase
import com.example.foodapps.domain.usecases.restaurant_case.GetRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

// Represents the possible states of the Home Screen UI related to categories
sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val categories: List<CategoryItem>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}


sealed interface RestaurantListUiState {
    object Loading : RestaurantListUiState
    data class Success(val restaurants: List<RestaurantFireBase>) : RestaurantListUiState
    data class Error(val message: String) : RestaurantListUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMenuCategoriesUseCase: GetMenuCategoriesUseCase,
    private val getRestaurantsUseCase: GetRestaurantsUseCase
) : ViewModel() {
    private val _categoryUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val categoryUiState: StateFlow<HomeUiState> = _categoryUiState.asStateFlow()

    private val _restaurantUiState = MutableStateFlow<RestaurantListUiState>(RestaurantListUiState.Loading)
    val restaurantUiState: StateFlow<RestaurantListUiState> = _restaurantUiState.asStateFlow()

    init {
        loadCategories()
        loadRestaurants()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                getMenuCategoriesUseCase() // Execute the use case
                    .onStart { _categoryUiState.value = HomeUiState.Loading } // Set loading state
                    .collect { result ->
                        Log.d("HomeViewModel:::", "CategoriesOnSuccess:::: $result")
                        _categoryUiState.value = HomeUiState.Success(result) // Set success state
                    }
            } catch (error: Exception) {
                _categoryUiState.value = HomeUiState.Error(
                    error.message ?: "An unknown error occurred"
                ) // Set error state
            }
        }
    }

    fun loadRestaurants() {
        viewModelScope.launch {
            try {
                getRestaurantsUseCase().onStart { _restaurantUiState.value = RestaurantListUiState.Loading }
                    .collect { result ->
                        Log.d("HomeViewModel:::", "RestaurantListOnSuccess:::: $result")
                        _restaurantUiState.value = RestaurantListUiState.Success(result)
                    }
            }catch (error: Exception) {
                _restaurantUiState.value = RestaurantListUiState.Error(
                    error.message ?: "Failed to load restaurants"
                )
            }
        }
    }

}