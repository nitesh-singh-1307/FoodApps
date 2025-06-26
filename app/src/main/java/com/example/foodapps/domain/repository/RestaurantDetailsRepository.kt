package com.example.foodapps.domain.repository

import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.data.remote.model.RestaurantFireBase
import kotlinx.coroutines.flow.Flow

interface RestaurantDetailsRepository {
    fun getRestaurantDetails(restaurantId: String): Flow<RestaurantDetails>
    suspend fun updateRestaurantDetailsLike(
        restaurantId: String,
        menuItemId: String,
        newLikeStatus: Boolean
    ): Flow<RestaurantDetails>

    // Adds a menu item to favorites
    suspend fun addFavorite(userId: String, menuItemId: String, quantity: Int)

    // Removes a menu item from favorites
    suspend fun removeFavorite(userId: String, menuItemId: String)
}