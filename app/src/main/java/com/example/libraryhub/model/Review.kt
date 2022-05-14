package com.example.libraryhub.model

data class Review(
    val _id: String,
    val comment: String,
    val rate: Int,
    val user: User
)