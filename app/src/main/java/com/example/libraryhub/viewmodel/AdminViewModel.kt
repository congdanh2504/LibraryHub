package com.example.libraryhub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.model.RequestedBook
import com.example.libraryhub.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {

    private var recordSkip = 0;
    private var requestedSkip = 0

    private val _getRecordState = MutableLiveData<Boolean>()

    val getRecordState: LiveData<Boolean>
        get() = _getRecordState

    private val _record = MutableLiveData<BorrowerRecord>()

    val record: LiveData<BorrowerRecord>
        get() = _record

    private val _confirmState = MutableLiveData<Boolean>()

    val confirmState: LiveData<Boolean>
        get() = _confirmState

    private val _records = MutableLiveData<List<BorrowerRecord>>(listOf())

    val records: LiveData<List<BorrowerRecord>>
        get() = _records

    private val _requestedBooks = MutableLiveData<List<RequestedBook>>(listOf())

    val requestedBooks: LiveData<List<RequestedBook>>
        get() = _requestedBooks

    fun getRecordById(recordId: String) = viewModelScope.launch {
        adminRepository.getRecordById(recordId).let {
            if (it.isSuccessful) {
                _record.postValue(it.body())
                _getRecordState.postValue(true)
            } else {
                _getRecordState.postValue(false)
            }
        }
    }

    fun confirm(recordId: String) = viewModelScope.launch {
        adminRepository.confirm(recordId).let {
            if (it.isSuccessful) {
                _confirmState.postValue(true)
            } else {
                _confirmState.postValue(false)
            }
        }
    }

    fun getAllRecord() = viewModelScope.launch {
        adminRepository.getAllRecord(recordSkip).let {
            if (it.isSuccessful) {
                recordSkip += it.body()!!.size
                val newList: ArrayList<BorrowerRecord> = ArrayList(_records.value!!)
                newList.addAll(it.body()!!)
                _records.postValue(newList)
            }
        }
    }

    fun refreshAllRecord() = viewModelScope.launch {
        recordSkip = 0
        adminRepository.getAllRecord(recordSkip).let {
            if (it.isSuccessful) {
                recordSkip += it.body()!!.size
                _records.postValue(it.body())
            }
        }
    }

    fun getRequestedBooks() = viewModelScope.launch {
        adminRepository.getRequestedBooks(requestedSkip).let {
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
        adminRepository.getRequestedBooks(requestedSkip).let {
            if (it.isSuccessful) {
                requestedSkip += it.body()!!.size
                _requestedBooks.postValue(it.body())
            }
        }
    }

    fun acceptRequest(bookId: String) = viewModelScope.launch {
        adminRepository.acceptRequest(bookId).let {
            if (it.isSuccessful) {
                refreshRequestedBooks()
            }
        }
    }
}