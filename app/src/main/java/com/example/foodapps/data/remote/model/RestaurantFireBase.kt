package com.example.foodapps.data.remote.model

data class RestaurantFireBase(
    val id: String = "", // Firestore document ID
    val name: String = "",
    val address: String = "",
    val is_available: Boolean = false,
    val rating: String = "",
    val image_url: String = ""
)
