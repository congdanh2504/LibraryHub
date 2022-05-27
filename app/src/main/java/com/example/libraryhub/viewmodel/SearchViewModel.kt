package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.Category
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val categoryRepository: CategoryRepository,
                                          private val bookRepository: BookRepository): ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    private val _searchingBook = MutableLiveData<List<Book>>()

    val categories: LiveData<List<Category>>
        get() = _categories

    val searchingBook: LiveData<List<Book>>
        get() = _searchingBook

    init {
        getAllCategory()
    }

    fun search(query: String, skip: Int) = viewModelScope.launch {
        bookRepository.search(query, skip).let {
            if (it.isSuccessful) {
                _searchingBook.postValue(it.body())
            }
        }
    }

    private fun getAllCategory() = viewModelScope.launch {
        categoryRepository.getAllCategory().let {
            if (it.isSuccessful) {
                _categories.postValue(it.body())
            }
        }
    }
}