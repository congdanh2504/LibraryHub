package com.example.libraryhub.api

import com.example.libraryhub.model.Notification
import retrofit2.Response
import retrofit2.http.*

interface NotificationAPI {

    @POST("user/deviceid")
    suspend fun addDeviceId(@Query("deviceId") deviceId: String): Response<Void>

    @DELETE("user/deviceid")
    suspend fun deleteDeviceId(@Query("deviceId") deviceId: String): Response<Void>

    @GET("user/notification")
    suspend fun getNotifications(): Response<List<Notification>>

    @DELETE("user/notification/{notificationId}")
    suspend fun deleteNotification(@Path("notificationId") notificationId: String): Response<Void>

    @PATCH("user/notification")
    suspend fun seenNotifications(): Response<Void>
}