package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(val name: String, val picture : String, val _id : String = ""): Parcelable
