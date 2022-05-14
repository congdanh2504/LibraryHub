package com.example.libraryhub.repository

import com.example.libraryhub.api.BookAPI
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookAPI: BookAPI) {

    suspend fun getDiscover() = bookAPI.getDiscover()
}