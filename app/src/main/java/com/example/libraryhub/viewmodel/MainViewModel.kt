package com.example.libraryhub.viewmodel

import androidx.lifecycle.*
import com.example.libraryhub.model.Notification
import com.example.libraryhub.repository.DataStoreRepository
import com.example.libraryhub.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    val dataStoreUser = dataStoreRepository.readUser.asLiveData()
    val dataStoreCart = dataStoreRepository.readCart.asLiveData()

    private val _notifications = MutableLiveData<List<Notification>>()

    val notification: LiveData<List<Notification>>
        get() = _notifications

    fun getNotifications() = viewModelScope.launch {
        notificationRepository.getNotifications().let {
            if (it.isSuccessful) {
                _notifications.postValue(it.body())
            }
        }
    }
}