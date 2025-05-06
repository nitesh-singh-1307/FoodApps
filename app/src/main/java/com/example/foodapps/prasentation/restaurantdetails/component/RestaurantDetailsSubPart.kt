package com.example.foodapps.prasentation.restaurantdetails.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.data.remote.model.FeaturedItem
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
fun DirectionsButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        Text("Get Directions", color = Color.Black)
    }
}

@Composable
fun RestaurantDetailsSubPart(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp)

    ) {
        RestaurantHeader(
            name = "Lorem Ipsum New Street",
            hours = "12PM to 23PM",
            status = "Open now",
            title = "Brunch and fusion food specialists"
        )
        Spacer(modifier = Modifier.height(24.dp))
        DirectionsButton(onClick = { /* Handle directions button click */ })
        Spacer(modifier = Modifier.height(32.dp))
        FeaturedItemTitle(title = "Featured Items")

        Spacer(modifier = Modifier.height(16.dp))
        FeaturedItemsSection(
            itemsFeatured = listOf(
                FeaturedItem(
                    name = "Lorem Ipsum",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    price = "$12.99"
                ),
                FeaturedItem(
                    name = "Lorem Ipsum",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    price = "$12.99"
                ),
                FeaturedItem(
                    name = "Lorem Ipsum",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    price = "$12.99"
                ),
                FeaturedItem(
                    name = "Lorem Ipsum",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    price = "$12.99"
                ),
            )
        )
//        Spacer(modifier = Modifier.height(24.dp))
    }
//    SeeMoreButton(onClick = { /* Handle see more button click */ })

}

@Composable
fun FeaturedItemTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun FeaturedItemsSection(itemsFeatured: List<FeaturedItem>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(itemsFeatured.size) { featuredItemIndex ->
            val featuredItem = itemsFeatured[featuredItemIndex]
            FeaturedItemRow(item = featuredItem)
            Spacer(modifier = Modifier.width(16.dp))
        }

    }
}

@Composable
fun FeaturedItemRow(item: FeaturedItem) {
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween){
            Image(
                painter = painterResource(R.drawable.foodimg), // Replace with your image resource
                contentDescription = "Dish image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.scrim
                )
            }
        }

}

@Composable
fun RestaurantHeader(
    name: String,
    hours: String,
    status: String,
    title: String
) {
    Column(
        modifier = Modifier.fillMaxWidth() // Adjust the value as needed

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_map),
                contentDescription = "Add",
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "Add",
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = hours,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// Preview
@Preview(showBackground = true)
@Composable
fun RestaurantDetailsSubPartPreview() {
    FoodAppsTheme {
        RestaurantDetailsSubPart(modifier = Modifier)
    }
}
