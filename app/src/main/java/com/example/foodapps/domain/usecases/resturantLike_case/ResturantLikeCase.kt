package com.example.foodapps.domain.usecases.resturantLike_case

import com.example.foodapps.domain.repository.LikedItemsRepository
import javax.inject.Inject

class ResturantLikeCase @Inject constructor(
    private val likedItemsRepository: LikedItemsRepository
){
    operator fun invoke() = likedItemsRepository.getLikedMenuItems()
}