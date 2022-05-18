package com.example.libraryhub.api

import com.example.libraryhub.model.Book
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.Review
import retrofit2.Response
import retrofit2.http.*

interface BookAPI {

    @GET("book/discover")
    suspend fun getDiscover(): Response<List<List<Book>>>

    @GET("book/{bookId}")
    suspend fun getBook(@Path("bookId") bookId: String): Response<Book>

    @GET("book/category/{categoryId}")
    suspend fun getBooksByCategory(@Path("categoryId") categoryId: String): Response<List<Book>>

    @GET("book/search")
    suspend fun search(@Query("query") query: String): Response<List<Book>>

    @POST("user/addreview/{bookId}")
    suspend fun addReview(@Path("bookId") bookId: String, @Body review: Review): Response<Void>

    @POST("user/checkquantity")
    suspend fun checkQuantity(@Body body: List<CartBook>): Response<Boolean>

    @POST("user/borrowbook")
    suspend fun borrowBook(@Body body: List<CartBook>): Response<Void>

    @GET("user/borrowingbooks")
    suspend fun getBorrowingBooks(): Response<BorrowerRecord>

    @GET("user/recentbooks")
    suspend fun getRecentBooks(): Response<List<Book>>

    @POST("user/returnbook/{recordId}")
    suspend fun returnBooks(@Path("recordId") recordId: String): Response<Void>
}