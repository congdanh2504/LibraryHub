package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {

    private val _getRecordState = MutableLiveData<Boolean>()

    val getRecordState: LiveData<Boolean>
        get() = _getRecordState

    private val _record = MutableLiveData<BorrowerRecord>()

    val record: LiveData<BorrowerRecord>
        get() = _record

    private val _confirmState = MutableLiveData<Boolean>()

    val confirmState: LiveData<Boolean>
        get() = _confirmState

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
}