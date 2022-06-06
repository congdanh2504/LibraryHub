package com.example.libraryhub.repository

import com.example.libraryhub.api.NotificationAPI
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val notificationAPI: NotificationAPI) {

    suspend fun addDeviceId(deviceId: String) = notificationAPI.addDeviceId(deviceId)

    suspend fun deleteDeviceId(deviceId: String) = notificationAPI.deleteDeviceId(deviceId)

    suspend fun getNotifications() = notificationAPI.getNotifications()

    suspend fun deleteNotification(notificationId: String) = notificationAPI.deleteNotification(notificationId)

    suspend fun seenNotifications() = notificationAPI.seenNotifications()
}