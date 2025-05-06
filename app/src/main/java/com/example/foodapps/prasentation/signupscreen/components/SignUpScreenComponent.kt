package com.example.foodapps.prasentation.signupscreen.components
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp

// ========================
// ==== Theme/Constants ===
// ========================
object SignUpScreenDimens{
    val HorizontalPadding = 35.dp
    val VerticalSpacing = 24.dp
    val SmallSpacing = 8.dp
}

// ========================
// ==== State Management ==
// ========================
@Stable
data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)




@Composable
fun rememberSignUpState() = rememberSaveable(
    stateSaver = Saver(
        save = { state -> listOf(state.name, state.email, state.password, state.isPasswordVisible) },
        restore = { restoredList ->
            SignUpUiState(
                name = restoredList[0] as String,
                email = restoredList[1] as String,
                password = restoredList[2] as String,
                isPasswordVisible = restoredList[3] as Boolean
            )
        }
    )
) {
    mutableStateOf(SignUpUiState())
}