package com.example.foodapps.prasentation.signinscreen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import com.example.foodapps.prasentation.signupscreen.components.SignUpUiState

object SignInDimens {
    val HorizontalPadding = 24.dp
    val LargeSpacing = 35.dp
    val SmallSpacing = 5.dp
    val ImageCornerRadius = 190.dp
    val ButtonPadding = 24.dp
    val TextSpacing = 2.dp
}


// State Management
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)

@Composable
fun rememberLoginState() = rememberSaveable(
    stateSaver = Saver(
        save = { state -> listOf(state.email, state.password, state.isPasswordVisible) },
        restore = { restoredList ->
            LoginUiState(
                email = restoredList[0] as String,
                password = restoredList[1] as String,
                isPasswordVisible = restoredList[2] as Boolean
            )
        }
    )
) {
    mutableStateOf(LoginUiState())
}



