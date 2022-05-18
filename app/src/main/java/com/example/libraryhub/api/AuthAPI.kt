package com.example.libraryhub.api

import com.example.libraryhub.model.AuthUser
import com.example.libraryhub.model.User
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

    @POST("auth/signinwithgoogle/{idToken}")
    suspend fun signInWithGoogle(@Path("idToken") idToken: String): Response<String>

    @GET("profile")
    suspend fun getProfile(): Response<User>

    @Headers("Content-Type: application/json")
    @POST("auth/signin")
    suspend fun signIn(@Body authUser: AuthUser): Response<String>

    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    suspend fun signUp(@Body authUser: AuthUser): Response<String>
}