package com.example.libraryhub.model

data class Package(
    val name: String,
    val time: Int,
    val price: Int,
    val benefit: String,
    val booksPerLoan: Int
)