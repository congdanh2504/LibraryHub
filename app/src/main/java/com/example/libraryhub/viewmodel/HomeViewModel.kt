package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {

    val _borrowerRecord = MutableLiveData<BorrowerRecord?>()

    val borrowerRecord: LiveData<BorrowerRecord?>
        get() = _borrowerRecord

    val _recentBooks = MutableLiveData<List<Book>>()

    val recentBooks: LiveData<List<Book>>
        get() = _recentBooks


    fun getBorrowingBooks() = viewModelScope.launch {
        try {
            bookRepository.getBorrowingBooks().let {
                if (it.isSuccessful) {
                    _borrowerRecord.postValue(it.body())
                }
            }
        } catch (e: Exception) {
            Log.d("AAA", "Null value")
        }
    }

    fun getRecentBooks() = viewModelScope.launch {
        bookRepository.getRecentBooks().let {
            if (it.isSuccessful && it.body()!!.isNotEmpty()) {
                _recentBooks.postValue(it.body())
            }
        }
    }

    fun returnBooks(recordId: String) = viewModelScope.launch {
        bookRepository.returnBooks(recordId).let {
            if (it.isSuccessful) {
                getBorrowingBooks()
            }
        }
    }
}