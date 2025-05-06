package com.example.foodapps.prasentation.restaurantdetails

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.domain.usecases.login_case.LoginUseCase
import com.example.foodapps.prasentation.signupscreen.RegisterState
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ResturantDetailsState {
    object Idle : ResturantDetailsState()
    object Loading : ResturantDetailsState()
    data class Success(val user: FirebaseUser) : ResturantDetailsState()
    data class Error(val message: String) : ResturantDetailsState()
}

@HiltViewModel
class ResturantDetailsViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<ResturantDetailsState>(ResturantDetailsState.Idle)
    val loginState: StateFlow<ResturantDetailsState> get() = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ResturantDetailsState.Loading
            val result = loginUseCase(email, password)
            Log.d("TAG", "check login result::::::nitesh0987 $result")
            _loginState.value = if (result.isSuccess) {
                ResturantDetailsState.Success(result.getOrNull()!!)
            } else {
                ResturantDetailsState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}