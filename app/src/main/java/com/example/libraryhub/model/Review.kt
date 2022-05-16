package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val comment: String,
    val rate: Double,
    val user: User,
    val _id: String = ""
): Parcelable