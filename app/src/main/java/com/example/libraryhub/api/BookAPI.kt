package com.example.libraryhub.api

import com.example.libraryhub.model.Book
import retrofit2.Response
import retrofit2.http.GET

interface BookAPI {

    @GET("book/discover")
    suspend fun getDiscover(): Response<List<List<Book>>>
}