package com.example.foodapps.domain.repository

import com.example.foodapps.data.remote.model.CategoryItem
import kotlinx.coroutines.flow.Flow
import kotlin.Result

interface MenuCategoryRepository {
    // Returns a Flow for real-time updates or just a list for one-time fetch
    fun getMenuCategories(): Flow<List<CategoryItem>> // Use Result for error handling
}