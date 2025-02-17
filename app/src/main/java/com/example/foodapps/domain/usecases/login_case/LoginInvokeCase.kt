package com.example.foodapps.domain.usecases.login_case

import com.example.foodapps.data.remote.model.LoginResponse
import com.example.foodapps.domain.repository.LoginRepository
import com.example.foodapps.domain.repository.Result

class LoginInvokeCase(
    private val repository: LoginRepository
) {
     operator fun invoke(email: String, password: String) : Result<LoginResponse> {
         if (email.isBlank() || password.isBlank()) {
             return Result.Error("Invalid input")
         }
         return repository.login(email, password)    }
}