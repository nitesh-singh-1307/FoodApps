package com.example.foodapps.domain.usecases.restaurantdetails_case

import com.example.foodapps.domain.repository.RestaurantDetailsRepository

class RemoveFavoriteUseCase(private val repository: RestaurantDetailsRepository) {
    suspend operator fun invoke(userId: String, menuItemId: String) =
        repository.removeFavorite(userId, menuItemId)
}