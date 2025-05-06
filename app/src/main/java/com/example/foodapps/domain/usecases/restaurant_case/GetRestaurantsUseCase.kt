package com.example.foodapps.domain.usecases.restaurant_case

import com.example.foodapps.domain.repository.RestaurantRepository
import javax.inject.Inject

class GetRestaurantsUseCase  @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke() = repository.getRestaurants()
}