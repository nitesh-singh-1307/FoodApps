package com.example.foodapps.prasentation.welcomescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.foodapps.R
import com.example.foodapps.common.PrimaryButton
import com.example.foodapps.prasentation.signinscreen.components.SignInDimens
import com.example.foodapps.prasentation.welcomescreen.WelcomeScreenEvent
import com.example.foodapps.ui.theme.AppTheme

@Composable
fun WelcomeScreen(
    onEvent: (WelcomeScreenEvent) -> Unit,
    onLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // welcome image
        Image(
            painter = painterResource(id = R.drawable.foodimg),
            contentDescription = "welcome page",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Semi-transparent overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Adjust alpha for transparency
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom

        ) {
            Text(
                text = stringResource(id = R.string.welcome_message),
                color = AppTheme.colorScheme.separator,
                style = AppTheme.typography.labelLarge
            )
            Text(
                text = stringResource(id = R.string.welcome_description),
                color = AppTheme.colorScheme.separator,
                style = AppTheme.typography.labelNormal
            )

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SignInDimens.ButtonPadding),
                label = stringResource(R.string.str_login_button),
                onClick = {
                    onEvent(WelcomeScreenEvent.SaveAppEntry)
                    onLogin()
                },
                enabled = true,
                showLoading = true
            )
//            Spacer(modifier = Modifier.weight(0.01f))
        }
    }
}