package com.example.foodapps.prasentation.mainscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.Screen
import com.example.foodapps.domain.usecases.app_entry.AppEntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _startDestination = mutableStateOf(Screen.Login.route)
    val startDestination: State<String> = _startDestination

    // handle event expose the event out side
    init {
        viewModelScope.launch {
            appEntryUseCases.readAppEntry().onEach {
                _startDestination.value = Screen.Login.route
            }
        }
    }
}