package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.adapter.AdapterBook
import com.example.libraryhub.model.Book
import com.example.libraryhub.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {

    private val _books = MutableLiveData<List<Book>>()

    val books: LiveData<List<Book>>
        get() = _books

    fun getBooksByCategory(categoryId: String) = viewModelScope.launch {
        bookRepository.getBooksByCategory(categoryId).let {
            if (it.isSuccessful) {
                _books.postValue(it.body())
            }
        }
    }
}