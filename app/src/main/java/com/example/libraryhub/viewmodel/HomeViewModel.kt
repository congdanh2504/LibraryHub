package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.model.Category
import com.example.libraryhub.model.RequestedBook
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    init {
        getAllCategory()
    }

    val _borrowerRecord = MutableLiveData<BorrowerRecord?>()

    val borrowerRecord: LiveData<BorrowerRecord?>
        get() = _borrowerRecord

    val _recentBooks = MutableLiveData<List<Book>>()

    val recentBooks: LiveData<List<Book>>
        get() = _recentBooks

    private val _uploadPictureState = MutableLiveData<Boolean>()

    val uploadPictureState: LiveData<Boolean>
        get() = _uploadPictureState

    private val _categories = MutableLiveData<List<Category>>()

    val categories: LiveData<List<Category>>
        get() = _categories

    private val _picture =  MutableLiveData<String>()

    var picture: String = ""

    private val _requestState = MutableLiveData<Boolean>()

    val requestState: LiveData<Boolean>
        get() = _requestState

    private fun getAllCategory() = viewModelScope.launch {
        categoryRepository.getAllCategory().let {
            if (it.isSuccessful) {
                _categories.postValue(it.body())
            }
        }
    }

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

    fun uploadPicture(file: MultipartBody.Part) = viewModelScope.launch {
        bookRepository.uploadPicture(file).let {
            if (it.isSuccessful) {
                picture = it.body()!!
                _uploadPictureState.postValue(true)
            } else {
                _uploadPictureState.postValue(false)
            }
        }
    }

    fun requestBook(requestedBook: RequestedBook) = viewModelScope.launch {
        bookRepository.requestBook(requestedBook).let {
            if (it.isSuccessful) {
                _requestState.postValue(true)
            } else {
                _requestState.postValue(false)
            }
        }
    }
}