package com.example.libraryhub.repository

import com.example.libraryhub.api.AdminAPI
import javax.inject.Inject

class AdminRepository @Inject constructor(private val adminAPI: AdminAPI) {

    suspend fun getRecordById(recordId: String) = adminAPI.getRecordById(recordId)

    suspend fun confirm(recordId: String) = adminAPI.confirm(recordId)

    suspend fun getAllRecord() = adminAPI.getAllRecord()

    suspend fun getRequestedBooks() = adminAPI.getRequestedBooks()

    suspend fun acceptRequest(bookId: String) = adminAPI.acceptRequest(bookId)
}