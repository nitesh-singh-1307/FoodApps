package com.example.foodapps.domain.repository

import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.data.remote.model.RestaurantFireBase
import kotlinx.coroutines.flow.Flow

interface RestaurantDetailsRepository {
    fun getRestaurantDetails(restaurantId: String): Flow<List<RestaurantDetails>>
}