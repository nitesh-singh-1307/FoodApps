package com.example.foodapps.domain.repository

import com.example.foodapps.data.remote.model.RestaurantFireBase
import com.example.foodapps.data.remote.model.RestaurantList
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getRestaurants(): Flow<List<RestaurantFireBase>>
}