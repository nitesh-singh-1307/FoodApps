package com.example.foodapps.domain.usecases.app_entry

import com.example.foodapps.domain.manger.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(): Flow<Boolean> {
      return localUserManager.readAppEntry()
    }
}