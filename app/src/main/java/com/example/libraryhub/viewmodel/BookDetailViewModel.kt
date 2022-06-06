package com.example.libraryhub.viewmodel

import androidx.lifecycle.*
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.Review
import com.example.libraryhub.model.User
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.repository.DataStoreRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val dataStoreUser = dataStoreRepository.readUser.asLiveData()
    val dataStoreCart = dataStoreRepository.readCart.asLiveData()

    fun saveCart(cart: ArrayList<CartBook>) = viewModelScope.launch {
        val gson = Gson()
        val json = gson.toJson(cart)
        dataStoreRepository.saveCart(json)
    }

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book>
        get() = _book

    fun getBook(bookId: String) = viewModelScope.launch {
        bookRepository.getBook(bookId).let {
            if (it.isSuccessful) {
                _book.postValue(it.body())
            }
        }
    }

    fun addReview(bookId: String, review: Review) = viewModelScope.launch {
        bookRepository.addReview(bookId, review).let {
            if (it.isSuccessful) {
                getBook(bookId)
            }
        }
    }
}