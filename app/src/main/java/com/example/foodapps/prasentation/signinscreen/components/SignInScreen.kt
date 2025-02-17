package com.example.foodapps.prasentation.signinscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.foodapps.R
import com.example.foodapps.common.EmailInput
import com.example.foodapps.common.PasswordInput
import com.example.foodapps.common.PrimaryButton
import com.example.foodapps.prasentation.nvgraph.Route
import com.example.foodapps.prasentation.signinscreen.LoginState
import com.example.foodapps.prasentation.signinscreen.SignInViewModel
import com.example.foodapps.ui.theme.AppTheme

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by signInViewModel.loginState.collectAsState()
    val verticalScroll = rememberScrollState()

    when (val state = loginState) {
        is LoginState.Loading -> CircularProgressIndicator()
        is LoginState.Error -> Text("Error: ${state.message}", color = Color.Red)
        is LoginState.Success -> {
            // Navigate to Home Screen on successful login
            LaunchedEffect(Unit) {
                navController.navigate(Route.HomeScreen.route) {
                    popUpTo(Route.SignInScreen.route) { inclusive = true } // Clear back stack
                }
            }
        }
        LoginState.Idle -> {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.size.large)
        ) {
            val passwordFocus = FocusRequester()
            val aboutFocus = FocusRequester()

            // food image
            Image(
                painter = painterResource(id = R.drawable.foodimg),
                contentDescription = "Login Page",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 190.dp,
                            bottomEnd = 190.dp,
                            topStart = 0.dp,
                            topEnd = 0.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .padding(start = 33.dp),
                text = stringResource(id = R.string.title_hello),
                color = AppTheme.colorScheme.loginTitle,
                style = AppTheme.typography.labelLarge,
            )
// Email filed
            EmailInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 33.dp, end = 33.dp),
                email = email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                onEmailChanged = { email = it },
            )
// Password filed
            PasswordInput(
                modifier = Modifier
                    .focusRequester(passwordFocus)
                    .fillMaxWidth()
                    .padding(start = 33.dp, end = 33.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { aboutFocus.requestFocus() }),
                password = password,
                onPasswordChanged = { password = it },
            )
            // Login button
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                label = stringResource(id = R.string.str_login_button),
                onClick = { signInViewModel.login(email, password)},
                enabled = loginState !is LoginState.Loading

            )
// forgot password
            Text(
                modifier = Modifier.clickable { },
                text = stringResource(id = R.string.str_forgot_password),
                color = AppTheme.colorScheme.loginTitle,
                style = AppTheme.typography.labelLarge
            )
            Row {
                Text(
                    text = stringResource(id = R.string.str_dont_have_account),
                    color = AppTheme.colorScheme.secondary,
                    style = AppTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.clickable { },
                    text = stringResource(id = R.string.str_sign_up),
                    color = AppTheme.colorScheme.loginTitle,
                    style = AppTheme.typography.labelLarge
                )
            }

        }
    }


}