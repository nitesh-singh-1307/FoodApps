package com.example.foodapps.common

object AuthManager {
    fun isUserLoggedIn(): Boolean {
        // Replace with actual logic, e.g., check token from DataStore or SharedPreferences
        return com.google.firebase.auth.FirebaseAuth.getInstance().currentUser != null
    }
}