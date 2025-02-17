package com.example.foodapps.data.remote.repository

import com.example.foodapps.data.remote.FoodApiService
import com.example.foodapps.data.remote.model.LoginRequest
import com.example.foodapps.data.remote.model.LoginResponse
import com.example.foodapps.domain.repository.LoginRepository
import javax.inject.Inject
import com.example.foodapps.domain.repository.Result
class LoginRepositoryImpl @Inject constructor(private val foodApiService: FoodApiService) : LoginRepository {
    override fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = foodApiService.getLogin(LoginRequest(email, password))
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Login failed")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }
}