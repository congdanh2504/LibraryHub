package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(private val notificationRepository: NotificationRepository): ViewModel() {

    private val _deleteState = MutableLiveData<Boolean>()

    val deleteState: LiveData<Boolean>
        get() = _deleteState

    fun deleteDeviceId(deviceId: String) = viewModelScope.launch {
        notificationRepository.deleteDeviceId(deviceId).let {
            _deleteState.postValue(true)
        }
    }
}