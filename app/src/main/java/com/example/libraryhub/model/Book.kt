package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Book(
    val author: String,
    val avgRate: Double,
    val borrowedNum: Int,
    val category: Category,
    val description: String,
    val location: @RawValue Location,
    val name: String,
    val picture: String,
    val price: Int,
    val publishYear: Int,
    val publisher: String,
    val quantity: Int,
    val reviews: @RawValue List<Review>,
    val type: String,
    val _id: String = "",
): Parcelable