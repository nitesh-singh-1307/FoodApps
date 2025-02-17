package com.example.foodapps.domain.manger
import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
suspend fun saveAppEntry()
suspend fun readAppEntry(): Flow<Boolean>
}