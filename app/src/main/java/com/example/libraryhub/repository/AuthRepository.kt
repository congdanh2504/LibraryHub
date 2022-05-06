package com.example.libraryhub.repository

import com.example.libraryhub.api.AuthAPI
import com.example.libraryhub.model.AuthUser
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authAPI: AuthAPI) {

    suspend fun signInWithGoogle(idToken: String) = authAPI.signInWithGoogle(idToken)

    suspend fun getProfile() = authAPI.getProfile()

    suspend fun signIn(authUser: AuthUser) = authAPI.signIn(authUser)

    suspend fun signUp(authUser: AuthUser) = authAPI.signUp(authUser)
}