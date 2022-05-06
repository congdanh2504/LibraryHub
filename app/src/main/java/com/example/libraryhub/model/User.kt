package com.example.libraryhub.model

data class User(
    val _id: String,
    val borrowingNum: Int,
    val email: String,
    val picture: String,
    val role: String,
    val username: String
)