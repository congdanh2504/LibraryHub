package com.example.libraryhub.viewmodel

import androidx.lifecycle.*
import com.example.libraryhub.model.Notification
import com.example.libraryhub.repository.DataStoreRepository
import com.example.libraryhub.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val dataStoreUser = dataStoreRepository.readUser.asLiveData()

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

    fun deleteNotification(notificationId: String) = viewModelScope.launch {
        notificationRepository.deleteNotification(notificationId).let {
            if (it.isSuccessful) {
                notificationRepository.getNotifications()
            }
        }
    }

    fun seenNotifications() = viewModelScope.launch {
        notificationRepository.seenNotifications()
    }
}