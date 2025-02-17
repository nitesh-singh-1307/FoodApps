package com.example.foodapps.domain.usecases.app_entry

import com.example.foodapps.domain.manger.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}