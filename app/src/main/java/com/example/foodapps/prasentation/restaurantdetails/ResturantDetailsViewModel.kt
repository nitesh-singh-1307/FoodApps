package com.example.foodapps.prasentation.restaurantdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.domain.usecases.restaurantdetails_case.AddFavoriteUseCase
import com.example.foodapps.domain.usecases.restaurantdetails_case.RemoveFavoriteUseCase
import com.example.foodapps.domain.usecases.restaurantdetails_case.RestaurantDetailsUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// Using a sealed interface here allows us to represent a restricted set of possible states
// for the restaurant details screen. This makes the state management more explicit and
// helps prevent unexpected states. It also makes it easier to handle these states exhaustively.

sealed interface ResturantDetailsState {
    object Loading : ResturantDetailsState
    data class Success(val restaurantDetails: RestaurantDetails) : ResturantDetailsState
    data class Error(val message: String) : ResturantDetailsState
}


@HiltViewModel
class ResturantDetailsViewModel
@Inject constructor(
    private val resturantDetailsUseCase: RestaurantDetailsUseCase,
    savedStateHandle: SavedStateHandle,
    private val firebaseAuth: FirebaseAuth, // For current user ID
    private val addFavoriteUseCase: AddFavoriteUseCase,
    ) : ViewModel() {
    private val _uiStateRestrauntDetails = MutableStateFlow<ResturantDetailsState>(ResturantDetailsState.Loading)
    val uiStateRestrauntDetails: StateFlow<ResturantDetailsState> = _uiStateRestrauntDetails.asStateFlow()
    private val restaurantId: String // Declare it

    companion object {
        private const val TAG = "RestaurantMenuVM"
    }

    private val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid
    val userId = currentUserId

    init {
        // Or handle the null case more gracefully, e.g., show an error state
        restaurantId = savedStateHandle.get<String>("id") ?: throw IllegalStateException("Restaurant ID not found in navigation arguments")
        // Now you can use this restaurantId
        if (restaurantId.isNotBlank()) {
            loadresturantDetails()
            loadMenuItemsWithFavoriteStatus()
        } else {
            _uiStateRestrauntDetails.value = ResturantDetailsState.Error("Restaurant ID is missing.")
        }
    }

    fun addToFavorites(menuItemId: String) {
        val userId = currentUserId
        if (userId.isNullOrBlank()) {
            Log.w(TAG, "Cannot add to favorites: User not logged in.")
            // Optionally show a message to log in
            return
        }
        if (restaurantId.isBlank()) { // Also ensure we have restaurant context if needed by repo
            Log.e(TAG, "Cannot add to favorites: Restaurant ID missing.")
            return
        }
        viewModelScope.launch {
            try {
                val updatedDetails = addFavoriteUseCase(userId, menuItemId)
                // Handle updated RestaurantDetails
//                _uiStateRestrauntDetails.value = ResturantDetailsState.Success() // Set success state
            } catch (e: Exception) {
                Log.e("ResturantDetailsViewModel", "Error adding to favorites: ${e.message}")
                _uiStateRestrauntDetails.value = ResturantDetailsState.Error("Failed to add to favorites: ${e.message}")
            }
        }
    }

    private fun loadMenuItemsWithFavoriteStatus() {
        if (restaurantId.isBlank()) {
            _uiStateRestrauntDetails.value = ResturantDetailsState.Error("Restaurant ID is missing.")
            return
        }

        if (userId.isNullOrBlank()) {
            // Handle case where user is not logged in - maybe fetch menu without favorite status
            Log.w(TAG, "User not logged in, fetching menu without favorite status.")
            viewModelScope.launch {
                try {
                    resturantDetailsUseCase(restaurantId) // Execute the use case
                        .onStart {
                            _uiStateRestrauntDetails.value = ResturantDetailsState.Loading
                        } // Set loading state
                        .collect { result ->
                            _uiStateRestrauntDetails.value = ResturantDetailsState.Success(result) // Set success state
                            Log.d("ResturantDetailsViewModel", "ResturantDetailsSuccess: $result")
                        }
                } catch (error: Exception) {
                    _uiStateRestrauntDetails.value = ResturantDetailsState.Error(
                        error.message ?: "Failed to load menu items"
                    ) // Set error state
                }
            }
        }

    }

    private fun loadresturantDetails() {
        if (restaurantId.isBlank()) {
            _uiStateRestrauntDetails.value = ResturantDetailsState.Error("Restaurant ID is missing.")
            return
        }
        viewModelScope.launch {
            try {
                resturantDetailsUseCase(restaurantId) // Execute the use case
                    .onStart {
                        _uiStateRestrauntDetails.value = ResturantDetailsState.Loading
                    } // Set loading state
                    .collect { result ->
                        _uiStateRestrauntDetails.value = ResturantDetailsState.Success(result) // Set success state
                        Log.d("ResturantDetailsViewModel", "ResturantDetailsSuccess: $result")

                    }
            } catch (error: Exception) {
                _uiStateRestrauntDetails.value = ResturantDetailsState.Error(
                    error.message ?: "Failed to load menu items"
                ) // Set error state
            }
        }
    }

    fun toggleRestaurantLikeStatus(menuItemId: String, currentIsLiked: Boolean) {
        val userId = currentUserId
        if (userId.isNullOrBlank()) {
            Log.w(TAG, "Cannot toggle favorite: User not logged in.")
            // Optionally show a message to log in
            return
        }
        if (restaurantId.isBlank()) { // Also ensure we have restaurant context if needed by repo
            Log.e(TAG, "Cannot toggle favorite: Restaurant ID missing.")
            return
        }
        viewModelScope.launch {
            try {
                val newLikeStatus = !currentIsLiked // Toggle the status
                resturantDetailsUseCase(restaurantId, menuItemId, newLikeStatus)
                    .collect { details ->

                        // Handle updated RestaurantDetails
                        _uiStateRestrauntDetails.value = ResturantDetailsState.Success(details) // Set success state
                    }

            } catch (e: Exception) {
                Log.e("ResturantDetailsViewModel", "Error toggling restaurant like status: ${e.message}")
            }
        }
    }

}