package com.example.foodapps.data.remote.repository

import android.util.Log
import com.example.foodapps.domain.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor() : NotificationRepository {

    // Placeholder for the actual implementation
    // This should interact with Firebase or any other notification service
    private val _messages = MutableStateFlow<List<RemoteMessage>>(emptyList())
    override val messages: StateFlow<List<RemoteMessage>> = _messages


    override fun addMessage(message: RemoteMessage) {
        // Logic to add a message
        _messages.value = _messages.value + message
    }

    override fun removeMessage(messageId: String?) {
        // Logic to remove a message by ID
        messageId?.let {
            _messages.value = _messages.value.filter { it.messageId != messageId }
        }
    }
    override suspend fun getFcmToken(): String? {
        // Logic to get FCM token
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            Log.e("FCM", "Error getting token", e)
            null
        }
    }
}