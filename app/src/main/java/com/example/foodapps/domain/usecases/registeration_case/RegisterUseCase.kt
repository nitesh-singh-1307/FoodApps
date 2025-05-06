package com.example.foodapps.domain.usecases.registeration_case

import android.util.Log
import com.example.foodapps.domain.repository.AuthRepository
import com.example.foodapps.domain.repository.UserDataRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(email: String, password: String, name: String): Result<Unit> {
        Log.d("TAG", "CheckRegisterUseCase:::: $email:::${password}:::${name}")
        return authRepository.register(email, password).map { user ->
            userDataRepository.saveUserData(user, name, email)
        }
    }
}
