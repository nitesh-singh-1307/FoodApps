package com.example.foodapps.common

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.foodapps.domain.repository.NotificationRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FCMService : FirebaseMessagingService() {
    @Inject lateinit var notificationRepository: NotificationRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        notificationRepository.addMessage(remoteMessage)
    }

    override fun onNewToken(token: String) {
        // Handle token refresh if needed
        super.onNewToken(token)
        // Send the new token to your server
    }

}