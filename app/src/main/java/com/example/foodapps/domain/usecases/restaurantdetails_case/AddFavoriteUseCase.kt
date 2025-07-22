package com.example.foodapps.domain.usecases.restaurantdetails_case

import com.example.foodapps.domain.repository.RestaurantDetailsRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val repository: RestaurantDetailsRepository) {
    suspend operator fun invoke(userId: String, menuItemId: String) = repository.addFavorite(userId, menuItemId ,1)
}