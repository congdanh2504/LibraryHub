package com.example.libraryhub.model

data class CartBook(
    val id: String,
    val name: String,
    val picture: String,
    val author: String,
    var quantity: Int,
    var isSelected : Boolean
)