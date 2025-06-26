package com.example.foodapps.prasentation.signinscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodapps.R
import com.example.foodapps.common.EmailInput
import com.example.foodapps.common.NextCommanButton
import com.example.foodapps.common.PasswordInput
import com.example.foodapps.common.PrimaryButton
import com.example.foodapps.prasentation.signinscreen.components.LoginUiState
import com.example.foodapps.prasentation.signinscreen.components.SignInDimens
import com.example.foodapps.prasentation.signinscreen.components.rememberLoginState
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme

@Composable
fun SignInScreen(
    signInViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val uiState by signInViewModel.loginState.collectAsState()
    val scrollState = rememberScrollState()
    var loginState by rememberLoginState()

    when (uiState) {
        is AuthState.Idle -> {}
        is AuthState.Loading -> CircularProgressIndicator()
        is AuthState.Success -> {
            Text("Login Successful")
            LaunchedEffect(uiState) {
                onNavigateToMain()
            }
        }
        is AuthState.Error -> Text("Error: ${(uiState as AuthState.Error).message}")
    }

    Scaffold(
        topBar = { SignInTopAppBar() }
    ) { paddingValues ->
        SignInContent(
            paddingValues = paddingValues,
            uiState = loginState,
            onEmailChange = { loginState = loginState.copy(email = it) },
            onPasswordChange = { loginState = loginState.copy(password = it) },
            onSignInClick = { signInViewModel.login( loginState.email, loginState.password) },
            onNavigateToSignUp = onNavigateToSignUp,
            scrollState = scrollState
        )
    }

}

@Composable
private fun SignInContent(
    paddingValues: PaddingValues,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SignInDimens.LargeSpacing)
    ) {
        Spacer(modifier = Modifier.height(SignInDimens.SmallSpacing))
        SignInHeader()
        SignInForm(
            uiState = uiState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onSignInClick = onSignInClick
        )
        ForgotPasswordLink()
        SignUpPrompt(onNavigateToSignUp)
    }
}

@Composable
private fun SignInTopAppBar() {
    Image(
        painter = painterResource(id = R.drawable.foodimg),
        contentDescription = stringResource(R.string.sign_in_image_desc),
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = SignInDimens.ImageCornerRadius,
                    bottomEnd = SignInDimens.ImageCornerRadius
                )
            ),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun SignInHeader() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = SignInDimens.HorizontalPadding),
        text = stringResource(R.string.title_hello),
        color = AppTheme.colorScheme.loginTitle,
        style = AppTheme.typography.labelLarge
    )
}

@Composable
private fun SignInForm(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {
    val passwordFocus = FocusRequester()
    val aboutFocus = FocusRequester()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SignInDimens.HorizontalPadding),
        verticalArrangement = Arrangement.spacedBy(SignInDimens.LargeSpacing)
    ) {
        EmailInput(
            email = uiState.email,
            onEmailChanged = onEmailChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() })
        )

        PasswordInput(
            password = uiState.password,
            onPasswordChanged = onPasswordChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { aboutFocus.requestFocus() }),
            focusRequester = passwordFocus
        )

//        SignInButton(
//            isLoading = true,
//            onClick = onSignInClick
//        )

        NextCommanButton(
            onClick = onSignInClick,
            text = stringResource(R.string.str_login_button),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SignInButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    PrimaryButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SignInDimens.ButtonPadding),
        label = stringResource(R.string.str_login_button),
        onClick = onClick,
        enabled = !isLoading,
        showLoading = isLoading
    )
}
@Composable
private fun ForgotPasswordLink() {
    Text(
        modifier = Modifier.clickable { /* Handle forgot password */ },
        text = stringResource(R.string.str_forgot_password),
        color = AppTheme.colorScheme.loginTitle,
        style = AppTheme.typography.labelLarge
    )
}

@Composable
private fun SignUpPrompt(onNavigateToSignUp: () -> Unit) {
    Row {
        Text(
            text = stringResource(R.string.str_dont_have_account),
            color = AppTheme.colorScheme.secondary,
            style = AppTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.width(SignInDimens.TextSpacing))
        Text(
            modifier = Modifier.clickable(onClick = onNavigateToSignUp),
            text = stringResource(R.string.str_sign_up),
            color = AppTheme.colorScheme.loginTitle,
            style = AppTheme.typography.labelLarge
        )
    }
}

@Composable
fun AuthorScreen(
    signInViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
//    SignInScreen(
//        onNavigateToMain = {},
//        onNavigateToSignUp = {}
//    )
}

@Preview(showBackground = true, device = "id:pixel_5", apiLevel = 35)
@Composable
private fun SignInScreenPreview() {
    FoodAppsTheme {
        AuthorScreen(
            onNavigateToMain = {},
            onNavigateToSignUp = {}
        )
    }
}
