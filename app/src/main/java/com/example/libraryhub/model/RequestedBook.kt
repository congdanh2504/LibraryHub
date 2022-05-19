package com.example.libraryhub.model

data class RequestedBook(
    val name: String,
    val description: String,
    val author: String,
    val category: Category,
    var picture: String? = "",
    var isAccepted : Boolean = false,
)