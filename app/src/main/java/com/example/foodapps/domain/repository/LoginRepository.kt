package com.example.foodapps.domain.repository

import com.example.foodapps.data.remote.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LoginRepository{
    fun login(email: String, password: String): Result<LoginResponse>
}