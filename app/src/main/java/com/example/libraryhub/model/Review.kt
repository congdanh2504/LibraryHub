package com.example.libraryhub.model

data class Review(
    val comment: String,
    val rate: Double,
    val user: User,
    val _id: String = ""
)