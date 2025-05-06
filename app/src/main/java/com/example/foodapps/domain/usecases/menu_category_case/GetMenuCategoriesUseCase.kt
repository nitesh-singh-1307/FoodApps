package com.example.foodapps.domain.usecases.menu_category_case

import com.example.foodapps.domain.repository.MenuCategoryRepository
import javax.inject.Inject // If using Hilt/Dagger

class GetMenuCategoriesUseCase @Inject constructor(
    private val repository: MenuCategoryRepository
) {
    // Use invoke operator for cleaner calling syntax
    operator fun invoke() = repository.getMenuCategories()
}