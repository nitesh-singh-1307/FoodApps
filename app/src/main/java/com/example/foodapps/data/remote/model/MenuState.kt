package com.example.foodapps.data.remote.model

data class MenuState(
    val categories: List<String> = listOf("Starters", "Appetizers", "Main Course", "Dessert"),
    val selectedCategory: String = "Starters",
    val selectedDishes: List<MenuCategory> = emptyList()
)