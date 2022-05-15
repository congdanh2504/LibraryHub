package com.example.libraryhub.model

data class Location(val face : Int,val column : Int,val row : Int){
    override fun toString(): String {
        return "Face ${this.face} | Col ${this.column} | Row ${this.row}"
    }
}
