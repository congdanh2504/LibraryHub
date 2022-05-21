package com.example.libraryhub.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationAPI {

    @POST("user/deviceid")
    suspend fun addDeviceId(@Query("deviceId") deviceId: String): Response<Void>

    @DELETE("user/deviceid")
    suspend fun deleteDeviceId(@Query("deviceId") deviceId: String): Response<Void>
}