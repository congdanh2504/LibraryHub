package com.example.libraryhub.repository

import com.example.libraryhub.api.BookAPI
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.RequestedBook
import com.example.libraryhub.model.Review
import okhttp3.MultipartBody
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookAPI: BookAPI) {

    suspend fun getDiscover() = bookAPI.getDiscover()

    suspend fun getBooksByCategory(categoryId: String, skip: Int) = bookAPI.getBooksByCategory(categoryId, skip)

    suspend fun search(query: String, skip: Int) = bookAPI.search(query, skip)

    suspend fun getBook(bookId: String) = bookAPI.getBook(bookId)

    suspend fun addReview(bookId: String, review: Review) = bookAPI.addReview(bookId, review)

    suspend fun borrowBook(books: List<CartBook>) = bookAPI.borrowBook(books)

    suspend fun checkQuantity(books: List<CartBook>) = bookAPI.checkQuantity(books)

    suspend fun getBorrowingBooks() = bookAPI.getBorrowingBooks()

    suspend fun getRecentBooks(skip: Int) = bookAPI.getRecentBooks(skip)

    suspend fun getRequestedBooks(skip: Int) = bookAPI.getRequestedBooks(skip)

    suspend fun returnBooks(recordId: String) = bookAPI.returnBooks(recordId)

    suspend fun uploadPicture(file: MultipartBody.Part) = bookAPI.uploadPicture(file)

    suspend fun requestBook(requestedBook: RequestedBook) = bookAPI.requestBook(requestedBook)

    suspend fun deleteRequestedBook(bookId: String) = bookAPI.deleteRequestedBook(bookId)
}