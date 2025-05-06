package com.example.foodapps.prasentation.signupscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.domain.usecases.registeration_case.RegisterUseCase
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log


sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(val registerUseCase: RegisterUseCase) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> get() = _registerState
    fun register(name: String, email: String, password: String) {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()
        val trimmedName = name.trim()

        if (trimmedEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _registerState.value = RegisterState.Error("Invalid email format")
            return
        }
        Log.d("TAG", "CheckEmail:::: $trimmedEmail:::${trimmedPassword}:::${trimmedName}")
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = registerUseCase(trimmedEmail, trimmedPassword, trimmedName)
            Log.d("TAG", "check result:::::: $result")
            _registerState.value = if (result.isSuccess) {
                RegisterState.Success("Registration successful")
            } else {
                RegisterState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

}