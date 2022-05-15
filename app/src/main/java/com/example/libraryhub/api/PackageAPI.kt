package com.example.libraryhub.api

import com.example.libraryhub.model.Package
import retrofit2.Response
import retrofit2.http.GET

interface PackageAPI {

    @GET("package")
    suspend fun getPackages(): Response<List<Package>>
}