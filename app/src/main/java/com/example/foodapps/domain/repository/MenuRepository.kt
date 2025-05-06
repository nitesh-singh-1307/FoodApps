package com.example.foodapps.domain.repository

import com.example.foodapps.R
import com.example.foodapps.data.remote.model.MenuCategory

class MenuRepository {
    fun getMenuItems(): Map<String, List<MenuCategory>> {
        return mapOf(
            "Starters" to listOf(
                MenuCategory(
                    name = "Dish name",
                    description = "Lorem ipsum dolor sit amet, consectetur.",
                    price = 14.90,
                    imageRes = R.drawable.foodimg
                )
            )
            // Other categories
        )
    }
}