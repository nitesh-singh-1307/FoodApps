package com.example.foodapps.prasentation.filters
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.ui.theme.AppTheme
import androidx.compose.material3.RangeSlider
import com.example.foodapps.prasentation.filters.components.CategoriesFilterSection
import com.example.foodapps.prasentation.filters.components.FilterConstants
import com.example.foodapps.prasentation.filters.components.FilterState
import com.example.foodapps.prasentation.filters.components.OptionsSection
import com.example.foodapps.prasentation.filters.components.PriceRangeSection
import com.example.foodapps.prasentation.filters.components.rememberFilterState
import com.example.foodapps.prasentation.filters.components.toggleCategory
import com.example.foodapps.prasentation.filters.components.toggleOption


val placeHolderColor = Color(0xFF98A2CC)
val textLabelColor = Color(0xFF250A82)



//data class FilterState(
//    val selectedCategory: Set<Any?> = emptySet(),
//    val priceRange: ClosedFloatingPointRange<Float> = 0f..70f,
//    val selectedOptions: Set<Any?> = emptySet()
//)



@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    onSearch: (FilterState) -> Unit,
    onReset: () -> Unit
) {
    var state by rememberFilterState()
//
//    Scaffold(
//        topBar = {
//            FilterTopBar(
//                onReset = {
//                    state = FilterState()
//                    onReset()
//                })
//        },
//        bottomBar = {
//            SearchButton(onClick = { onSearch(state) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .navigationBarsPadding()
//                    .padding(16.dp)
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = modifier
//                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp)
//        ) {
//
//            CategoriesFilterSection(
//                selectedCategories = state.selectedCategory,
//                onCategorySelected = { category ->
//                    state = state.copy(
//                        selectedCategory = state.selectedCategory.toMutableSet().apply {
//                            if (contains(category)) remove(category) else add(category)
//                        }
//                    )
//                }
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//            PriceSliderScreen(
//                initialRange = state.priceRange,
//                onPriceRangeChanged = {range -> state = state.copy(priceRange = range)})
//
//            Spacer(modifier = Modifier.height(24.dp))
//            OptionsSection(
//                selectedOptions = state.selectedOptions,
//                onOptionSelected = { option ->
//                    state = state.copy(
//                        selectedOptions = state.selectedOptions.toMutableSet().apply {
//                            if (contains(option)) remove(option) else add(option)
//                        }
//                    )
//                }
//            )
//        }
//    }

    Scaffold(
        topBar = { FilterTopBar(onReset = { state = FilterState(); onReset() }) },
        bottomBar = { SearchButton(onClick = { onSearch(state) }) }
    ) { innerPadding ->
        FilterContent(
            modifier = modifier.padding(innerPadding),
            state = state,
            onCategorySelected = { category ->
                state = state.toggleCategory(category)
            },
            onPriceRangeChanged = { range ->
                state = state.copy(priceRange = range)
            },
            onOptionSelected = { option ->
                state = state.toggleOption(option)
            }
        )
    }
}

//@Composable
//fun PriceSliderScreen(
//    initialRange: ClosedFloatingPointRange<Float>,
//    onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        PriceSlider(
//            value = initialRange.start, // Use initialRange directly
//            onValueChange = { newValue ->
//                onPriceRangeChanged(newValue..initialRange.endInclusive) // Update the range via callback
//            },
//            valueRange = 0f..70f
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//        // Display the current selected value (optional)
//    }
//}

//@Composable
//fun OptionsSection(
//    selectedOptions: Set<Any?>,
//    onOptionSelected: (String) -> Unit) {
//    val options = listOf("Free Delivery", "Restaurant ticket", "Gluten-free")
//    FilterSection(title = "OPTIONS"){
//        Column {
//            options.forEach { option ->
//                val isSelected = selectedOptions.contains(option)
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onOptionSelected(option) }
//                        .padding(vertical = 12.dp)
//                ) {
//                    Text(
//                        text = option,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = if (selectedOptions.contains(option)) {
//                            MaterialTheme.colorScheme.primary
//                        } else {
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
//                        },
//                        modifier = Modifier.weight(1f)
//                    )
//                    Icon(
//                        painter = painterResource(
//                            if (isSelected) {
//                                R.drawable.ic_check_enabled
//                            } else {
//                                R.drawable.ic_check_enabled
//                            }
//                        ),
//                        contentDescription = if (isSelected) "Selected" else "Not selected",
//                        tint = if (isSelected) {
//                            MaterialTheme.colorScheme.primary
//                        } else {
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                        },
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
//            }
//            TextButton(onClick = { /* Handle more options */ }, modifier = Modifier.fillMaxWidth()) {
//                Text("More options >", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
//            }
//        }
//
//    }
//
//}

@Composable
private fun FilterContent(
    modifier: Modifier = Modifier,
    state: FilterState,
    onCategorySelected: (String) -> Unit,
    onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CategoriesFilterSection(
            selectedCategories = state.selectedCategories,
            onCategorySelected = onCategorySelected
        )

        Spacer(modifier = Modifier.height(24.dp))

        PriceRangeSection(
            priceRange = state.priceRange,
            onPriceRangeChanged = onPriceRangeChanged
        )

        Spacer(modifier = Modifier.height(24.dp))

        OptionsSection(
            selectedOptions = state.selectedOptions,
            onOptionSelected = onOptionSelected
        )
    }
}











//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun CategoriesFilterSection(
//    selectedCategories: Set<Any?>,
//    onCategorySelected: (String) -> Unit
//) {
//    val categories = listOf("Gourmet", "Vegan", "Asian", "Pizza", "Brunch", "Cakes", "Mexican")
//    FilterSection(title = "CATEGORIES") {
//        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            categories.forEach { category ->
//                CategoryFilterChip(
//                    category = category,
//                    selected = selectedCategories.contains(category),
//                    onSelected = { onCategorySelected(category) }
//                )
//            }
//        }
//    }
//}

@Composable
fun FilterSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = AppTheme.typography.labelNormal,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun CategoryFilterChip(
    category: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit
) {
    AssistChip(
        onClick = { onSelected(!selected) },
        label = { Text(category) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (selected) {
                textLabelColor
            } else {
                placeHolderColor
            }
        )
    )
}

@Composable
fun SearchButton( onClick: () -> Unit,
                  modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text("Search", style = AppTheme.typography.labelLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTopBar(onReset: () -> Unit) {
    TopAppBar(
        title = { Text("Filters") },
        actions = {
            TextButton(onClick = onReset) {
                Text("Reset")
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview
@Composable
fun FilterScreenPreview() {
    MaterialTheme {
        FilterScreen(onSearch = {}, onReset = {})
    }
}
