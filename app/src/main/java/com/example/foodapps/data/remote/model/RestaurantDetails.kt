package com.example.foodapps.data.remote.model

data class RestaurantDetails(
    val id: String = "", // Subcollection document ID
    val item_name: String = "",
    val description: String = "",
    val price: String = "0.0", // Or Long/String if storing cents/formatted
    val category: String = "",
    val cuisine_type: String = "", // URL from Cloud Storage
    val item_image_url: String = "", // URL from Cloud Storage
    val is_available: Boolean = true
)
