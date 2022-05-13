package com.example.libraryhub.model

data class Location(val face : String,val column : String,val row : String){
    override fun toString(): String {
        return "Face ${this.face} | Column ${this.column} | Row ${this.row}"
    }
}
