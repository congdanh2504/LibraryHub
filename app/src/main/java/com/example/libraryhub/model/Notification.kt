package com.example.libraryhub.model

import java.util.*

class Notification(
    val _id: String,
    val createdDate: Date,
    val user: User,
    val message: String
)