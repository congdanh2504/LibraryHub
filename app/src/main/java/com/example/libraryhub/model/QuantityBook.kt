package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuantityBook(
    val book: Book,
    val quantity: Int
): Parcelable