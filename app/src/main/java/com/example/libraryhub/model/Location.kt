package com.example.libraryhub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(val face : Int,val column : Int,val row : Int) : Parcelable {
    override fun toString(): String {
        return "Face ${this.face} | Col ${this.column} | Row ${this.row}"
    }
}
