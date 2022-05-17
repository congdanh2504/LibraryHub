package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {

    private val _borrowerRecord = MutableLiveData<BorrowerRecord>()

    val borrowerRecord: LiveData<BorrowerRecord>
        get() = _borrowerRecord

    fun getBorrowingBooks() = viewModelScope.launch {
        try {
            bookRepository.getBorrowingBooks().let {
                if (it!!.isSuccessful) {
                    _borrowerRecord.postValue(it.body())
                }
            }
        } catch (e: Exception) {
            Log.d("AAA", "Null value")
        }

    }
}