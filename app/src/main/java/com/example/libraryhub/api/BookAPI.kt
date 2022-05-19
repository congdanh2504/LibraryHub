package com.example.libraryhub.api

import com.example.libraryhub.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

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

    @Multipart
    @POST("uploadpicture")
    suspend fun uploadPicture(@Part file: MultipartBody.Part): Response<String>

    @POST("user/requestbook")
    suspend fun requestBook(@Body body: RequestedBook): Response<Void>
}