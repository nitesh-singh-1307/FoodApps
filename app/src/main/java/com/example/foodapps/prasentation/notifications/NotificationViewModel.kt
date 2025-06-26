package com.example.foodapps.prasentation.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapps.domain.repository.NotificationRepository
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
): ViewModel() {
    val messages: StateFlow<List<RemoteMessage>> = notificationRepository.messages

    fun removeMessage(messageId: String?) {
        notificationRepository.removeMessage(messageId)
    }

    fun fetchFcmToken() {
        viewModelScope.launch {
            val token = notificationRepository.getFcmToken()
            // Handle token
            if (token != null) {
                Log.d("FCM", "Token: $token")
            } else {
                Log.e("FCM", "Failed to get token")
            }
        }
    }

}