package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class User(
    val _id: String,
    val currentPackage: Package? = null,
    val expiration: Date? = null,
    val email: String,
    var picture: String,
    val role: String,
    val username: String,
    val isBorrowing: Boolean
) : Parcelable {
    fun isExpire() = currentPackage == null || expiration!!.before(Calendar.getInstance().time)
}