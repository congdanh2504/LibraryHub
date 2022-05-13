package com.example.libraryhub.api

import com.example.libraryhub.model.Category
import retrofit2.Response
import retrofit2.http.GET

interface CategoryAPI {

    @GET("category")
    suspend fun getAllCategories(): Response<List<Category>>
}