package com.example.foodapps.prasentation.fevoriteScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.data.remote.model.RestaurantLikeDetails
import com.example.foodapps.domain.usecases.resturantLike_case.ResturantLikeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

// Using a sealed interface here allows us to represent a restricted set of possible states
// for the restaurant details screen. This makes the state management more explicit and
// helps prevent unexpected states. It also makes it easier to handle these states exhaustively.

sealed interface LikedItemsUiState {
    object Loading : LikedItemsUiState
    data class Success(val likedItems: List<RestaurantLikeDetails>) : LikedItemsUiState
    data class Error(val message: String) : LikedItemsUiState
}


@HiltViewModel
class ResturantLikeViewModel
@Inject constructor(
    private val getLikedMenuItemsUseCase: ResturantLikeCase) : ViewModel() {

    private val _uiState = MutableStateFlow<LikedItemsUiState>(LikedItemsUiState.Loading)
    val uiState: StateFlow<LikedItemsUiState> = _uiState.asStateFlow()

    init {
        loadLikedItems()
    }

    fun loadLikedItems() {
        viewModelScope.launch {
            try {
                getLikedMenuItemsUseCase() // Execute the use case
                    .onStart { _uiState.value = LikedItemsUiState.Loading } // Set loading state
                    .collect { result ->
                        Log.d("ResturantDetailsViewModel:::", "LikedItemsOnSuccess:::: $result")
                        _uiState.value = LikedItemsUiState.Success(result) // Set success state
                    }
            } catch (error: Exception) {
                _uiState.value = LikedItemsUiState.Error(
                    error.message ?: "An unknown error occurred"
                ) // Set error state
            }
        }
    }

}