package com.example.foodapps.prasentation.orderScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.data.remote.model.OrderItem
import com.example.foodapps.ui.theme.FoodAppsTheme
import com.example.foodapps.common.NextCommanButton


//private val BackButtonPadding = PaddingValues(all = 16.dp)
@Composable
fun FoodOrderSummaryScreen(

) {
    val orderItems = listOf(
        OrderItem("Name of the dish", 21.90, 2)
    )
    Scaffold(
        topBar = { NavigationBackButton(onBackClick = { /* Handle back button click */ },
            text = "Checkout",
            modifier = Modifier.fillMaxWidth()
            ) }
    ) { innerPadding ->
        // Scaffold content here
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            FoodOrderSummaryContent(orderItems, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))
            NextCommanButton(
                onClick = { /* Handle see more button click */ },
                text = "Order",
                modifier = Modifier.fillMaxWidth()
                )
        }
    }
}

@Composable
fun FoodOrderSummaryContent(orderItems: List<OrderItem>, modifier: Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 35.dp, end = 35.dp,top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            OrderItemsList(items = orderItems)
            Spacer(modifier = Modifier.height(24.dp))
            CouponSection()
            Spacer(modifier = Modifier.height(24.dp))
            TotalBreakdownBill(
                subtotal = 76.20,
                discount = 20.00,
                delivery = 5.90,
                total = 102.20
            )
        }
}




@Composable
fun TotalBreakdownBill(subtotal: Double, discount: Double, delivery: Double, total: Double,) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PriceRow("Subtotal", subtotal)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp, // Replace with desired thickness
            color = Color.Gray
        )
        PriceRow("Restaurant ticket", discount)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp, // Replace with desired thickness
            color = Color.Gray
        )
        PriceRow("Delivery", delivery)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp, // Replace with desired thickness
            color = Color.Gray // Replace with desired color
        )
        PriceRow("To Pay", total, isTotal = true)

    }
}

@Composable
fun PriceRow(label: String, amount: Double, isTotal: Boolean = false , modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Log.d("TAG","checkTotal:::::::::$isTotal")
        val textStyle = if (isTotal) {
            MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        } else {
            MaterialTheme.typography.bodyMedium
        }

        Text(
            modifier = modifier.weight(1f),
            text = label,
            style = textStyle,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        val symbol = when {
            label.contains("Restaurant ticket", ignoreCase = true) && amount != 0.0 -> "-"
            label.contains("Delivery", ignoreCase = true) && amount != 0.0 -> "+"
            else -> ""
        }

        Text(
            text = "$symbol $${"%.2f".format(amount)}",
            style = textStyle,
            textAlign = TextAlign.End,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )

    }
}

@Composable
fun CouponSection(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        CouponContent()
    }
}
@Composable
fun CouponContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_food_coupon),
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.onSurfaceVariant, //Added tint
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Apply Coupon / Restaurant ticket",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
fun OrderItemRow(item: OrderItem, quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = "$ ${item.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Row {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable { if (quantity > 0) onQuantityChange(quantity - 1) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Minus", tint = Color.Gray)
            }
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(Color.Blue)
                    .border(1.dp, Color.Gray, CircleShape)
                    .clickable { onQuantityChange(quantity + 1) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = "Plus", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun OrderItemsList(items: List<OrderItem>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items.forEach { item ->
            var quantity by remember { mutableIntStateOf(item.quantity) }
            OrderItemRow(item = item, quantity = quantity, onQuantityChange = { newQuantity ->
                quantity = newQuantity
            })
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .padding(top = 2.dp)
                ,
                thickness = 1.dp, // Replace with desired thickness
                color = Color.Gray // Replace with desired color
            )
        }
    }
}


@Composable
private fun HeaderTextTitle(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier) {
    // Wrap the Text in a Box with the specified Alignment
    Box(modifier.wrapContentHeight(), contentAlignment = Alignment.TopStart) {
        Text(
            text = text,
            modifier = modifier.padding(start = 8.dp),
            style = style,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun NavigationBackButton(onBackClick: () -> Unit, text: String, modifier: Modifier) {
    Column(modifier.fillMaxWidth().padding(start = 22.dp , top = 28.dp)) {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_icon),
                    contentDescription = stringResource(R.string.back_button_desc),
                    tint = Color.Black,
                )
            }
        Spacer(modifier = Modifier.height(20.dp))
        HeaderTextTitle(
            text = text ,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFoodOrderSummaryScreen() {
    FoodAppsTheme {
        FoodOrderSummaryScreen()
    }
}