package com.example.libraryhub.model


data class Book (val bookId : String, val title : String, val thumbnail : String, val description : String, val author : String, val publisher : String,val price : Int,val location : Location)
//Book("123","this is the title","http://diaoc5sao.vn/wp-content/uploads/2021/11/The-Ukrainian-Book-Institute-Purchases-380.9-Thousand-Books-for-Public-Libraries1.jpeg","this is the dsescription","author unknown","publisher unknown")