package com.example.libraryhub.model

import java.util.*

data class User(
    val _id: String,
    val currentPackage: Package? = null,
    val expiration: Date? = null,
    val email: String,
    val picture: String,
    val role: String,
    val username: String
)