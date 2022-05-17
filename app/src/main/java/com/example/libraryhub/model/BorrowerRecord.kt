package com.example.libraryhub.model

import java.util.*

data class BorrowerRecord(
    val _id: String,
    val user: User,
    val books: List<QuantityBook>,
    val status: String,
    val createdDate: Date,
    val returnDate: Date
)