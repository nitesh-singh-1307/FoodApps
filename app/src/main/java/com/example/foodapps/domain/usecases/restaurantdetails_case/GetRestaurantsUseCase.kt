package com.example.foodapps.domain.usecases.restaurantdetails_case

import com.example.foodapps.domain.repository.RestaurantDetailsRepository
import com.example.foodapps.domain.repository.RestaurantRepository
import javax.inject.Inject

class RestaurantDetailsUseCase  @Inject constructor(
    private val repository: RestaurantDetailsRepository
) {
    operator fun invoke(restaurantId: String) = repository.getRestaurantDetails(restaurantId)
    suspend operator fun invoke(
        restaurantId: String,
        menuItemId: String,
        newLikeStatus: Boolean
    ) = repository.updateRestaurantDetailsLike(restaurantId, menuItemId, newLikeStatus)

}