package com.example.libraryhub.model

data class CartBook(
    val _id: String,
    val name: String,
    val picture: String,
    val author: String,
    var quantity: Int
)