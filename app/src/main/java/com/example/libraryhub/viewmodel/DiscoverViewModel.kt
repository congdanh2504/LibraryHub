package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.Category
import com.example.libraryhub.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val bookRepository: BookRepository): ViewModel() {

    private val _discover = MutableLiveData<List<List<Book>>>()

    val discover: LiveData<List<List<Book>>>
        get() = _discover

    init {
        getDiscover()
    }

    private fun getDiscover() = viewModelScope.launch {
        bookRepository.getDiscover().let {
            if (it.isSuccessful) {
                _discover.postValue(it.body())
            }
        }
    }
}