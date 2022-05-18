package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class BorrowerRecord(
    val _id: String,
    val user: User,
    val books: List<QuantityBook>,
    val status: String,
    val createdDate: Date,
    val returnDate: Date
): Parcelable