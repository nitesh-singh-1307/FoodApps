package com.example.foodapps.prasentation.homescreen.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.foodapps.data.remote.model.CategoryItem
import com.example.foodapps.ui.theme.Dimens.dynamicCategoryHeight

@Composable
fun CategoryItem(
    category: CategoryItem,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(dynamicCategoryHeight)
            .clickable { onCategorySelected(category.name) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            CategoryImage(category.image)
            CategoryName(category.name)
        }
    }
}

@Composable
private fun CategoryImage(imageUrl: String) {
    Log.d("CategoryImage", "Image URL: $imageUrl")
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl) // The URL from Firestore
            .crossfade(true).build(),
        contentDescription = null,
        modifier = Modifier.run {
            fillMaxWidth()
                .clip(CircleShape)
        }
    )
}

@Composable
private fun CategoryName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp)
    )
}