package com.example.libraryhub.api

import com.example.libraryhub.model.BorrowerRecord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AdminAPI {

    @GET("admin/record/{recordId}")
    suspend fun getRecordById(@Path("recordId") recordId: String): Response<BorrowerRecord>

    @GET("admin/record")
    suspend fun getAllRecord(): Response<List<BorrowerRecord>>

    @GET("admin/requestedbooks")
    suspend fun getRequestedBooks(): Response<Void>

    @POST("admin/confirm/{recordId}")
    suspend fun confirm(@Path("recordId") recordId: String): Response<Void>
}