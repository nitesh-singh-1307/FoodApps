package com.example.foodapps.domain.repository

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

interface NotificationRepository {
    val messages: StateFlow<List<RemoteMessage>>
    fun addMessage(message: RemoteMessage)
    fun removeMessage(messageId: String?)
    suspend fun getFcmToken(): String?
}