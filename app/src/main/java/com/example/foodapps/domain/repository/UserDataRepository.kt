package com.example.foodapps.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlin.Result

interface UserDataRepository {
    suspend fun saveUserData(user: FirebaseUser, name: String , email: String): Result<Unit>
}