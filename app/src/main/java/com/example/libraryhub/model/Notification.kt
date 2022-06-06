package com.example.libraryhub.model

import java.util.*

data class Notification(
    val _id: String,
    val createdDate: Date,
    val user: User,
    val message: String,
    val isSeen: Boolean
)