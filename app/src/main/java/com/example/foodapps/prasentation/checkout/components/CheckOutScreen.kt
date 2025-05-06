package com.example.foodapps.prasentation.checkout.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.prasentation.orderScreen.NavigationBackButton
import com.example.foodapps.prasentation.orderScreen.components.CircularButtonWithRightIcon
import com.example.foodapps.ui.theme.FoodAppsTheme
import com.example.foodapps.common.NextCommanButton
import kotlin.String

@Composable
fun CheckOutScreen() {
    Scaffold(
        topBar = { NavigationBackButton(onBackClick = { /* Handle back button click */ },
            text = "Checkout",
            modifier = Modifier.fillMaxWidth()
            ) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CheckOutComponent() }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 25.dp)
            ) {
                NextCommanButton(
                    onClick = { /* Handle see more button click */ },
                    text = "Process order",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CheckOutComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        PaymentMethodSection()
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
fun PaymentMethodSection() {
    var expandedSection by remember { mutableStateOf<String?>(null) }
    SectionCard {
        Column {
            // Header with arrow
            PaymentHeader(expanded = expandedSection == "payment", "PAYMENT METHOD") {
                expandedSection = if (expandedSection == "payment") {
                    null
                } else {
                    "payment"
                }
            }
            // Expandable content
            if (expandedSection == "payment") {
                PaymentDetails()
            }
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp, // Replace with desired thickness
                color = Color.Gray
            )
            PaymentHeader(expanded = expandedSection == "address", "DELIVERY ADDRESS") {
                expandedSection = if (expandedSection == "address") {
                    null
                } else {
                    "address"
                }
            }
            // Expandable content
            if (expandedSection == "address") {
                PaymentDetails()
            }
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp, // Replace with desired thickness
                color = Color.Gray
            )
            PaymentHeader(expanded = expandedSection == "delivery", "DELIVERY METHOD") {
                expandedSection = if (expandedSection == "delivery") {
                    null
                } else {
                    "delivery"
                }
            }
            // Expandable content
            if (expandedSection == "delivery") {
                PaymentDetails()
            }
            Spacer(modifier = Modifier.height(15.dp))


            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp, // Replace with desired thickness
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PaymentDetails() {
    var selectedPayment by remember { mutableStateOf<String?>(null) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PaymentMethods(
            logo = R.drawable.ic_visa,
            value = "**** **** **** 0025",
            isSelected = selectedPayment == "**** **** **** 0025",
            onSelect = {
                selectedPayment = "**** **** **** 0025"
            })
        PaymentMethods(
            logo = R.drawable.ic_paypal,
            value = "joana.dowson@mail.com",
            isSelected = selectedPayment == "joana.dowson@mail.com",
            onSelect = {
                selectedPayment = "joana.dowson@mail.com"
            })
        PaymentMethods(
            logo = R.drawable.ic_payment,
            value = "**** ****** 0974",
            isSelected = selectedPayment == "**** ****** 0974",
            onSelect = {
                selectedPayment = "**** ****** 0974"
            })
    }
}

@Composable
fun PaymentMethods(logo: Int, value: String, isSelected: Boolean = false, onSelect: () -> Unit) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
    Log.d("CheckOutScreen", "PaymentMethodSelected::::: $isSelected")
//    SideEffect {
//        Log.d("CheckOutScreen", "PaymentMethodSelected::::: $isSelected")
//    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ).selectable(
                selected = isSelected,
                onClick = {
                onSelect()
            })
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(48.dp),
                contentScale = ContentScale.Crop

            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier.padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (isSelected) {
                    CircularButtonWithRightIcon()
                }
            }
        }

    }

}

@Composable
fun PaymentHeader(expanded: Boolean, paymentTitle: String, onClick: () -> Unit) {
    val animationSpec: FiniteAnimationSpec<IntSize> = tween(
        durationMillis = 300, easing = LinearEasing
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = paymentTitle,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(
            onClick = onClick, modifier = Modifier
                .padding(start = 18.dp)
        ) {
            Icon(
                modifier = Modifier.animateContentSize(
                    animationSpec = animationSpec
                ),
                painter = if (expanded) painterResource(R.drawable.ic_arrowup) else painterResource(
                    R.drawable.ic_arrowdown
                ),
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun SectionCard(content: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCheckOrderSummaryScreen() {
    FoodAppsTheme {
        CheckOutScreen()
    }
}