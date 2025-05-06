package com.example.foodapps.prasentation.orderSuccess

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapps.R
import androidx.compose.material3.MaterialTheme
import com.example.foodapps.common.NextCommanButton
import com.example.foodapps.ui.theme.FoodAppsTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.foodapps.common.ConfettiAnimation

// Define spacing constants for consistency
private val ScreenPadding = 32.dp
private val MediumSpacing = 16.dp
private val LargeIconSize = 100.dp
private val SmallIconSize = 14.dp // Adjusted for better alignment with text
private val LinkIconStartPadding = 4.dp

/**
 * Displays the order success screen with an icon, messages, confetti,
 * action buttons, and a link to the orders section.
 *
 * @param modifier Modifier for the root composable.
 * @param successMessage The main success message text.
 * @param statusMessage The secondary message providing further instructions.
 * @param continueShoppingButtonText Text for the primary action button.
 * @param goToOrdersText Text for the link to navigate to orders.
 * @param onContinueShoppingClick Lambda triggered when the continue shopping button is clicked.
 * @param onGoToOrdersClick Lambda triggered when the go to orders link is clicked.
 */

@Composable
fun OrderSuccessScreen(
    modifier: Modifier = Modifier,
    successMessage: String? = null,
    statusMessage: String? = null,
    continueShoppingButtonText: String? = null,
    goToOrdersText: String? = null,
    onContinueShoppingClick: () -> Unit = {},
    onGoToOrdersClick: () -> Unit = {}
) {
    val actualSuccessMessage = successMessage ?: stringResource(R.string.order_success_default_title)
    val actualStatusMessage = statusMessage ?: stringResource(R.string.order_success_default_status)
    val actualContinueShoppingText = continueShoppingButtonText ?: stringResource(R.string.continue_shopping)
    val actualGoToOrdersText = goToOrdersText ?: stringResource(R.string.go_to_orders)

    Scaffold(
        modifier =  Modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues).padding(ScreenPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OrderSuccessIcon()
            Spacer(modifier = Modifier.height(MediumSpacing))
//            OrderSuccessMessage()
            // Main Success Message using Text directly
            Text(
                text = actualSuccessMessage,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
                // Color defaults to MaterialTheme.colorScheme.onSurface
            )
            // Confetti - Placed after main message for better visual flow
            ConfettiAnimation(
                particleCount = 200,
                animationDuration = 8000
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Status Check Message using Text directly
            Text(
                text = actualStatusMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Use theme color
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

//            StatusCheckMessage()
            Spacer(modifier = Modifier.height(16.dp))
            // Primary Action Button
            NextCommanButton(
                onClick = onContinueShoppingClick,
                text = actualContinueShoppingText,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
//            OrdersLink(onClick = {})
            // Link to Orders
            OrdersLink(
                text = actualGoToOrdersText,
                onClick = onGoToOrdersClick
            )

        }

    }
}
@Composable
fun OrdersLink( text: String,
                 onClick: () -> Unit,
                 modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null, // No visual indication (ripple)
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = MediumSpacing / 2), // Reduced padding for tighter link feel
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary, // Use theme primary color
            style = MaterialTheme.typography.labelMedium // Use appropriate theme style
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_forward_arrow),
            contentDescription = null, // Text provides context
            tint = MaterialTheme.colorScheme.primary, // Use theme primary color
            modifier = Modifier
                .padding(start = LinkIconStartPadding)
                .size(SmallIconSize) // Match text size better
        )
    }

//    Row(
//        modifier = Modifier
//            .clickable(
//                onClick = onClick,
//                indication = null,
//                interactionSource = remember { MutableInteractionSource() }
//            )
//            .padding(vertical = 16.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//
//    ) {
//        Text(
//            text = "Go to Orders",
//            color = Color.Blue,
//            fontSize = 12.sp
//        )
//        Icon(
//            painter = painterResource(id = R.drawable.ic_forward_arrow),
//            contentDescription = "Go to orders arrow",
//            tint = Color.Blue,
//            modifier = Modifier.padding(start = 3.dp).size(12.dp)
//        )
//    }
}

@Composable
fun OrderSuccessIcon(){
//    Image(
//        painter = painterResource(id = R.drawable.ic_success),
//        contentDescription = "Order success icon",
//        modifier = Modifier
//            .size(100.dp)
//            .padding(bottom = 16.dp)
//    )
    Image(
        painter = painterResource(id = R.drawable.ic_success),
        contentDescription = stringResource(R.string.order_success_icon_desc), // Use string resources
        modifier = Modifier
            .size(LargeIconSize)
        // Padding is handled by Spacers in the parent Column now
    )
}
@Composable
fun OrderSuccessMessage(text:String = "Order was Successfully"){
    CommonTest(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center // Ensure text content is centered
    )
}

@Composable
fun CommonTest(text: String = "",
               style: TextStyle,
               color: Color,
               fontWeight: FontWeight,
               fontSize: TextUnit,
               modifier: Modifier = Modifier, // Provide default Modifier
               textAlign: TextAlign? = null // Add textAlign parameter
               ){

    Text(
        text = text,
        style = style,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        modifier = modifier, // Apply the passed modifier
        textAlign = textAlign // Apply textAlign
        )

}

@Composable
fun StatusCheckMessage(text:String = "Check on the status of your order in the 'Orders' section."){
    CommonTest(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = if (text == "Check on the status of your order in the 'Orders' section.") FontWeight.Normal else FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center // Ensure text content is centered

    )
}

// --- Previews ---

@Preview(showBackground = true, name = "Order Success Light")
@Composable
private fun OrderSuccessScreenPreviewLight() {
    FoodAppsTheme(isDarkTheme = false) {
        OrderSuccessScreen()
    }
}

@Preview(showBackground = true, name = "Order Success Dark")
@Composable
private fun OrderSuccessScreenPreviewDark() {
    FoodAppsTheme(isDarkTheme = true) {
        OrderSuccessScreen()
    }
}


