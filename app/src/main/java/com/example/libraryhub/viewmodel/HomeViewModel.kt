package com.example.libraryhub.viewmodel

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

    private var requestedSkip = 0
    private var recentSkip = 0

    init {
        getAllCategory()
    }

    private val _borrowerRecord = MutableLiveData<BorrowerRecord?>()

    val borrowerRecord: LiveData<BorrowerRecord?>
        get() = _borrowerRecord

    private val _recentBooks = MutableLiveData<List<Book>>(listOf())

    val recentBooks: LiveData<List<Book>>
        get() = _recentBooks

    private val _requestedBooks = MutableLiveData<List<RequestedBook>>(listOf())

    val requestedBooks: LiveData<List<RequestedBook>>
        get() = _requestedBooks

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
            _borrowerRecord.postValue(null)
        }
    }

    fun getRecentBooks() = viewModelScope.launch {
        bookRepository.getRecentBooks(recentSkip).let {
            if (it.isSuccessful) {
                recentSkip += it.body()!!.size
                val newList: ArrayList<Book> = ArrayList(_recentBooks.value!!)
                newList.addAll(it.body()!!)
                _recentBooks.postValue(newList)
            }
        }
    }

    fun refreshRecentBooks() = viewModelScope.launch {
        recentSkip = 0
        bookRepository.getRecentBooks(recentSkip).let {
            if (it.isSuccessful) {
                recentSkip += it.body()!!.size
                _recentBooks.postValue(it.body())
            }
        }
    }

    fun getRequestedBooks() = viewModelScope.launch {
        bookRepository.getRequestedBooks(requestedSkip).let {
            if (it.isSuccessful) {
                requestedSkip += it.body()!!.size
                val newList: ArrayList<RequestedBook> = ArrayList(_requestedBooks.value!!)
                newList.addAll(it.body()!!)
                _requestedBooks.postValue(newList)
            }
        }
    }

    fun refreshRequestedBooks() = viewModelScope.launch {
        requestedSkip = 0
        bookRepository.getRequestedBooks(requestedSkip).let {
            if (it.isSuccessful) {
                requestedSkip += it.body()!!.size
                _requestedBooks.postValue(it.body())
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

    fun deleteRequestedBook(bookId: String) = viewModelScope.launch {
        bookRepository.deleteRequestedBook(bookId).let {
            if (it.isSuccessful) {
                refreshRequestedBooks()
            }
        }
    }
}