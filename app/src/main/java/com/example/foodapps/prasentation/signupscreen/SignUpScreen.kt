package com.example.foodapps.prasentation.signupscreen

import android.util.Log
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodapps.R
import com.example.foodapps.common.NextCommanButton
import com.example.foodapps.common.SignUpTextField
import com.example.foodapps.prasentation.signupscreen.components.SignUpScreenDimens
import com.example.foodapps.prasentation.signupscreen.components.SignUpUiState
import com.example.foodapps.prasentation.signupscreen.components.rememberSignUpState
import com.example.foodapps.ui.theme.AppTheme
import com.example.foodapps.ui.theme.FoodAppsTheme
import com.example.foodapps.ui.theme.textLabelColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToUp: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    var uiState by rememberSignUpState()
    val state by viewModel.registerState.collectAsState()
//2025-04-29 17:03:54.558  1105-1105  SignUpScreen
// com.example.foodapps
// D  state: Error(message=An internal error has occurred. [ CONFIGURATION_NOT_FOUND ])
    when (state) {
        is RegisterState.Idle -> {}
        is RegisterState.Loading -> CircularProgressIndicator()
        is RegisterState.Success -> {
            Text("Registration Successful")
            Log.d("SignUpScreen", "Registration Successful")
            LaunchedEffect(Unit) {
                onNavigateToMain()
            }
        }

        is RegisterState.Error -> Text("Error: ${(state as RegisterState.Error).message}")
    }
    Scaffold(
        topBar = { SignUpTopBar(onNavigateToUp) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        SignUpContent(
            innerPadding = innerPadding,
            uiState = uiState,
            onNameChange = { uiState = uiState.copy(name = it) },
            onEmailChange = { uiState = uiState.copy(email = it) },
            onPasswordChange = { uiState = uiState.copy(password = it) },
            onTogglePasswordVisibility = {
                uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
            },
            onCreateAccountClick = {
                viewModel.register(
                    uiState.name,
                    uiState.email,
                    uiState.password
                )
            },
            onNavigateToLogin = onNavigateToUp
        )
    }

}

@Composable
fun SignUpContent(
    innerPadding: PaddingValues,
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = SignUpScreenDimens.HorizontalPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(SignUpScreenDimens.SmallSpacing))
        SignUpHeader()
        Spacer(modifier = Modifier.height(SignUpScreenDimens.VerticalSpacing))
        SignUpForm(
            uiState = uiState,
            onNameChange = onNameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onTogglePasswordVisibility = onTogglePasswordVisibility
        )
        Spacer(modifier = Modifier.height(SignUpScreenDimens.VerticalSpacing))
        CreateAccountButton(onCreateAccountClick)
        Spacer(modifier = Modifier.height(SignUpScreenDimens.SmallSpacing))
        LoginPrompt(onNavigateToLogin)
    }
}

@Composable
private fun LoginPrompt(onNavigateToLogin: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.have_account_prompt),
            color = AppTheme.colorScheme.searchText,
            style = AppTheme.typography.labelLarge
        )
        Spacer(Modifier.width(2.dp))
        ClickableText(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = AppTheme.colorScheme.loginTitle)) {
                    append(stringResource(R.string.sign_in_button))
                }
            },
            onClick = { onNavigateToLogin() },
            style = AppTheme.typography.labelLarge
        )
    }
}

@Composable
private fun CreateAccountButton(onClick: () -> Unit) {
    NextCommanButton(
        onClick = onClick,
        text = stringResource(R.string.create_account_button),
        modifier = Modifier.fillMaxWidth()
    )
}

//n SignUpForm(
//uiState: SignUpUiState,
//onNameChange: (String) -> Unit,
//onEmailChange: (String) -> Unit,
//onPasswordChange: (String) -> Unit,
//onTogglePasswordVisibility: () -> Unit

@Composable
fun SignUpForm(
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit
) {
    SignUpTextField(
        value = uiState.name,
        onValueChange = onNameChange,
        label = stringResource(R.string.name_label),
        placeholder = stringResource(R.string.name_placeholder),
        keyboardType = KeyboardType.Text
    )
    Spacer(modifier = Modifier.height(SignUpScreenDimens.VerticalSpacing))

    SignUpTextField(
        value = uiState.email,
        onValueChange = onEmailChange,
        label = stringResource(R.string.email_label),
        placeholder = stringResource(R.string.email_placeholder),
        keyboardType = KeyboardType.Email
    )
    Spacer(modifier = Modifier.height(SignUpScreenDimens.VerticalSpacing))

    SignUpTextField(
        value = uiState.password,
        onValueChange = onPasswordChange,
        label = stringResource(R.string.password_label),
        placeholder = stringResource(R.string.password_placeholder),
        keyboardType = KeyboardType.Password,
        isPassword = true,
        isPasswordVisible = uiState.isPasswordVisible
    )
}

@Composable
private fun SignUpHeader() {
    Column {
        Text(
            text = stringResource(R.string.welcome_header),
            style = MaterialTheme.typography.labelLarge,
            color = textLabelColor
        )
        Spacer(modifier = Modifier.height(SignUpScreenDimens.SmallSpacing))
        Text(
            text = stringResource(R.string.sign_up_title),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = textLabelColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignUpTopBar(onNavigateUp: () -> Unit) {
    TopAppBar(
        title = { /* Empty title */ },
        navigationIcon = {
            NavigationIcon(
                onClick = onNavigateUp,
                contentDescription = stringResource(R.string.navigate_back_desc)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun NavigationIcon(
    onClick: () -> Unit,
    contentDescription: String
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = contentDescription
        )
    }
}


// ========================
// ======= Preview ========
// ========================
@Preview(showBackground = true, name = "Sign Up Screen Light")
@Composable
private fun SignUpScreenPreview() {
    FoodAppsTheme(isDarkTheme = false) {
        SignUpScreen(
            onNavigateToMain = {},
            onNavigateToUp = {}
        )
    }
}