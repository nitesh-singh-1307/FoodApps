package com.example.foodapps.prasentation.filters.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.prasentation.filters.FilterSection

// PriceRangeSection.kt
@Composable
fun PriceRangeSection(
    priceRange: ClosedFloatingPointRange<Float>,
    onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit
) {
    FilterSection(title = stringResource(R.string.price_range)) {
        RangeSlider(
            value = priceRange,
            onValueChange = onPriceRangeChanged,
            valueRange = FilterConstants.MIN_PRICE..FilterConstants.MAX_PRICE,
            steps = 7,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        PriceRangeLabels(priceRange)
    }
}

@Composable
private fun PriceRangeLabels(priceRange: ClosedFloatingPointRange<Float>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$${priceRange.start.toInt()}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "$${priceRange.endInclusive.toInt()}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}