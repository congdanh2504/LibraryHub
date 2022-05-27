package com.example.libraryhub.api

import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.model.RequestedBook
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AdminAPI {

    @GET("admin/record/{recordId}")
    suspend fun getRecordById(@Path("recordId") recordId: String): Response<BorrowerRecord>

    @GET("admin/record")
    suspend fun getAllRecord(@Query("skip") skip: Int, @Query("limit") limit: Int = 10): Response<List<BorrowerRecord>>

    @GET("admin/requestedbooks")
    suspend fun getRequestedBooks(@Query("skip") skip: Int, @Query("limit") limit: Int = 10): Response<List<RequestedBook>>

    @POST("admin/confirm/{recordId}")
    suspend fun confirm(@Path("recordId") recordId: String): Response<Void>

    @POST("admin/acceptrequest/{bookId}")
    suspend fun acceptRequest(@Path("bookId") bookId: String): Response<Void>
}