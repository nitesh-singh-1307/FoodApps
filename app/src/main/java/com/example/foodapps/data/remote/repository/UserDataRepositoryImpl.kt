package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.domain.repository.UserDataRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await


class UserDataRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) : UserDataRepository {
    override suspend fun saveUserData(user: FirebaseUser, name: String, email: String): Result<Unit> {
        return try {
            firestore.collection("users").document(user.uid).set(mapOf("name" to name , "email" to email)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}