package com.example.libraryhub.repository

import com.example.libraryhub.api.AdminAPI
import javax.inject.Inject

class AdminRepository @Inject constructor(private val adminAPI: AdminAPI) {

    suspend fun getRecordById(recordId: String) = adminAPI.getRecordById(recordId)

    suspend fun confirm(recordId: String) = adminAPI.confirm(recordId)

    suspend fun getAllRecord(skip: Int) = adminAPI.getAllRecord(skip)

    suspend fun getRequestedBooks(skip: Int) = adminAPI.getRequestedBooks(skip)

    suspend fun acceptRequest(bookId: String) = adminAPI.acceptRequest(bookId)
}