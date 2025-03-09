package com.example.foodapps.prasentation.filters.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.xr.compose.testing.toDp
import com.example.foodapps.R
import com.example.foodapps.ui.theme.AppTheme

data class FilterState(
    val selectedCategory: Set<Any?> = emptySet(),
    val priceRange: ClosedFloatingPointRange<Float> = 0f..70f,
    val selectedOptions: Set<Any?> = emptySet()
)

val FilterStateSaver: Saver<FilterState, *> = listSaver(
    save = { listOf(it.selectedCategory.toList(), it.priceRange.start, it.priceRange.endInclusive, it.selectedOptions.toList()) },
    restore = {
        FilterState(
            selectedCategory = (it[0] as List<*>).toSet(),
            priceRange = (it[1] as Float)..(it[2] as Float),
            selectedOptions = (it[3] as List<*>).toSet()
        )
    }
)

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    onSearch: (FilterState) -> Unit,
    onReset: () -> Unit
) {
    var state by rememberSaveable(stateSaver = FilterStateSaver) { mutableStateOf(FilterState()) }
//    var state by rememberSaveable { mutableStateOf(FilterState()) }
    Scaffold(
        topBar = {
            FilterTopBar(
                onReset = {
                    state = FilterState()
                    onReset()
                })
        },
        bottomBar = {
            SearchButton(onClick = { onSearch(state) },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            CategoriesFilterSection(
                selectedCategories = state.selectedCategory,
                onCategorySelected = { category ->
                    state = state.copy(
                        selectedCategory = state.selectedCategory.toMutableSet().apply {
                            if (contains(category)) remove(category) else add(category)
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            PriceRangeSection(
                priceRange = state.priceRange,
                onPriceRangeChanged = { range ->
                    state = state.copy(priceRange = range)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            OptionsSection(
                selectedOptions = state.selectedOptions,
                onOptionSelected = { option ->
                    state = state.copy(
                        selectedOptions = state.selectedOptions.toMutableSet().apply {
                            if (contains(option)) remove(option) else add(option)
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun OptionsSection(
    selectedOptions: Set<Any?>,
    onOptionSelected: (String) -> Unit) {
    val options = listOf("Free Delivery", "Restaurant ticket", "Gluten-free")
    FilterSection(title = "OPTIONS"){
        Column {
                options.forEach { option ->
                   val isSelected = selectedOptions.contains(option)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOptionSelected(option) }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selectedOptions.contains(option)) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(
                                if (isSelected) {
                                    R.drawable.ic_check_enabled
                                } else {
                                    R.drawable.ic_check_enabled
                                }
                            ),
                            contentDescription = if (isSelected) "Selected" else "Not selected",
                            tint = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            TextButton(onClick = { /* Handle more options */ }, modifier = Modifier.fillMaxWidth()) {
                Text("More options >", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
private fun PriceRangeSection(
    priceRange: ClosedFloatingPointRange<Float>,
    onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit
) {
    var currentRange by remember(priceRange) { mutableStateOf(priceRange) }
    val totalRange = 100f // Should match your slider's max value

    BoxWithConstraints(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        val sliderWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val textWidth = 64.dp // Estimated width of price text

        Column(modifier = Modifier.fillMaxWidth()) {
            // Price display container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                // Start price
                Text(
                    text = "$${"%.2f".format(currentRange.start)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = ((currentRange.start / totalRange) * sliderWidthPx).toDp() -
                                    textWidth / 2)
                )

                // End price
                Text(
                    text = "$${"%.2f".format(currentRange.endInclusive)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = ((currentRange.endInclusive / totalRange) * sliderWidthPx).toDp() -
                                    textWidth / 2)
                )
            }

            RangeSlider(
                value = currentRange,
                onValueChange = { newRange ->
                    currentRange = newRange
                    onPriceRangeChanged(newRange)
                },
                valueRange = 0f..100f,
                steps = (totalRange - 1).toInt(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
//
//@Composable
//fun PriceRangeSection(priceRange: ClosedFloatingPointRange<Float>,
//                      onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit) {
//    FilterSection(title = "PRICE RANGE") {
//        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
//            val sliderWidth = maxWidth
//            val totalRange = 100f // Your max value (adjust accordingly)
//            val density = LocalDensity.current
//            var currentRange by remember { mutableStateOf(priceRange) }
//
//            Column(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    val startPosition = with(density) {
//                        ((currentRange.start / totalRange) * sliderWidth.toPx()).toDp()
//                    }
//
//                    val endPosition = with(density) {
//                        ((currentRange.endInclusive / totalRange) * sliderWidth.toPx()).toDp()
//                    }
//                    Text(
//                        text = "$${"%.2f".format(currentRange.start)}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.offset(x = startPosition - 24.dp)
//                    )
//
//                    Text(
//                        text = "$${"%.2f".format(currentRange.endInclusive)}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.offset(x = endPosition - 24.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//                RangeSlider(
//                    value = currentRange,
//                    onValueChange = { newRange ->
//                        currentRange = newRange
//                        onPriceRangeChanged(newRange)
//                    },
//                    valueRange = 0f..100f,
//                    steps = 69,
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//            }
//        }
//
//    }
//}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesFilterSection(
    selectedCategories: Set<Any?>,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("Gourmet", "Vegan", "Asian", "Pizza", "Brunch", "Cakes", "Mexican")
    FilterSection(title = "CATEGORIES") {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.forEach { category ->
                CategoryFilterChip(
                    category = category,
                    selected = selectedCategories.contains(category),
                    onSelected = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
fun FilterSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
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
