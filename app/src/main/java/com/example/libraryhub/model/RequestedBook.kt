package com.example.libraryhub.model

data class RequestedBook(
    val name: String,
    val description: String,
    val author: String,
    val category: Category,
    var picture: String? = "",
    var requester: User? = null,
    var isAccepted : Boolean = false,
    val _id: String = ""
)