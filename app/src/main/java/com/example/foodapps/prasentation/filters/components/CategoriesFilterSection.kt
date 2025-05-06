package com.example.foodapps.prasentation.filters.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.prasentation.filters.CategoryFilterChip
import com.example.foodapps.prasentation.filters.FilterSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun CategoriesFilterSection(
    selectedCategories: Set<String>,
    onCategorySelected: (String) -> Unit
) {
    FilterSection(title = stringResource(R.string.categories)) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterConstants.CATEGORIES.forEach { category ->
                CategoryFilterChip(
                    category = category,
                    selected = selectedCategories.contains(category),
                    onSelected = { onCategorySelected(category) }
                )
            }
        }
    }
}