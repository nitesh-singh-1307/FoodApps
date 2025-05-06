package com.example.foodapps.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlin.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<FirebaseUser>
    suspend fun register(email: String, password: String): Result<FirebaseUser>
}
