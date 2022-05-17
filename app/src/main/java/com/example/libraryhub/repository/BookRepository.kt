package com.example.libraryhub.repository

import com.example.libraryhub.api.BookAPI
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.Review
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookAPI: BookAPI) {

    suspend fun getDiscover() = bookAPI.getDiscover()

    suspend fun getBooksByCategory(categoryId: String) = bookAPI.getBooksByCategory(categoryId)

    suspend fun search(query: String) = bookAPI.search(query)

    suspend fun getBook(bookId: String) = bookAPI.getBook(bookId)

    suspend fun addReview(bookId: String, review: Review) = bookAPI.addReview(bookId, review)

    suspend fun borrowBook(books: List<CartBook>) = bookAPI.borrowBook(books)

    suspend fun checkQuantity(books: List<CartBook>) = bookAPI.checkQuantity(books)

    suspend fun getBorrowingBooks() = bookAPI.getBorrowingBooks()
}