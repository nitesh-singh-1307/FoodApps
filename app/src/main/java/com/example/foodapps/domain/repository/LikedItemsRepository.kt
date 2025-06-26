package com.example.foodapps.domain.repository

import com.example.foodapps.data.remote.model.RestaurantDetails
import com.example.foodapps.data.remote.model.RestaurantLikeDetails
import kotlinx.coroutines.flow.Flow

interface LikedItemsRepository {
    fun getLikedMenuItems(): Flow<List<RestaurantLikeDetails>>
}