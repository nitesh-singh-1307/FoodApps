package com.example.foodapps.prasentation.signinscreen

import com.example.foodapps.data.remote.model.LoginResponse

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data class Success(val data: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()

}