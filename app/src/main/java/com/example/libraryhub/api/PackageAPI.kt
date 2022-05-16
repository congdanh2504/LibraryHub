package com.example.libraryhub.api

import com.example.libraryhub.model.Package
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PackageAPI {

    @GET("package")
    suspend fun getPackages(): Response<List<Package>>

    @POST("user/buypackage/{packageId}")
    suspend fun buyPackage(@Path("packageId") packageId: String): Response<Void>
}