package com.example.foodapps.prasentation.welcomescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel(){

    // handle event expose the event out side
    fun onEvent(event: WelcomeScreenEvent){
        when(event){
            is WelcomeScreenEvent.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            appEntryUseCases.saveAppEntry()
        }
    }

}