package com.example.libraryhub.model

import java.util.*

data class Invoice(val invoiceId : String, val bookList : List<Book>, val borrowDay : Date, val returnDay : Date, )

//Invoice("123", ,Date("23/02/2002"),Date("12/11/2123"))
