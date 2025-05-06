package com.example.foodapps.prasentation.filters.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable


// Constants
object FilterConstants {
    const val MIN_PRICE = 0f
    const val MAX_PRICE = 70f
    val CATEGORIES = listOf("Gourmet", "Vegan", "Asian", "Pizza", "Brunch", "Cakes", "Mexican")
    val OPTIONS = listOf("Free Delivery", "Restaurant ticket", "Gluten-free")
}

// State Management
data class FilterState(
    val selectedCategories: Set<String> = emptySet(),
    val priceRange: ClosedFloatingPointRange<Float> = 0f..70f,
    val selectedOptions: Set<String> = emptySet()
)


@Composable
fun rememberFilterState(): MutableState<FilterState> {
    return remember { mutableStateOf(FilterState()) }
}

private val FilterStateSaver = listSaver<FilterState,Any>(
    save = { listOf(it.selectedCategories, it.priceRange.start, it.priceRange.endInclusive, it.selectedOptions) },
    restore = {
        FilterState(
            selectedCategories = (it[0] as Set<String>),
            priceRange = (it[1] as Float)..(it[2] as Float),
            selectedOptions = (it[3] as Set<String>)
        )
    }
)



 fun FilterState.toggleCategory(category: String) = copy(
    selectedCategories = selectedCategories.toMutableSet().apply {
        if (contains(category)) remove(category) else add(category)
    }
)

 fun FilterState.toggleOption(option: String) = copy(
    selectedOptions = selectedOptions.toMutableSet().apply {
        if (contains(option)) remove(option) else add(option)
    }
)


