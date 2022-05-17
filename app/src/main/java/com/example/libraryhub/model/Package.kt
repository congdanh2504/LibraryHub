package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Package(
    val name: String,
    val time: Int,
    val price: Int,
    val benefit: String,
    val booksPerLoan: Int,
    val borrowDays: Int,
    val _id: String = ""
) : Parcelable