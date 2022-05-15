package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.Review
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {

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