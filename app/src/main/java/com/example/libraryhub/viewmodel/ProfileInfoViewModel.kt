package com.example.libraryhub.viewmodel

import androidx.lifecycle.*
import com.example.libraryhub.model.User
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.repository.DataStoreRepository
import com.example.libraryhub.repository.NotificationRepository
import com.example.libraryhub.utils.AppPreferences
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val authRepository: AuthRepository,
    private val bookRepository: BookRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val dataStoreUser = dataStoreRepository.readUser.asLiveData()

    var picture: String = ""

    private val _uploadPictureState = MutableLiveData<Boolean>()

    val uploadPictureState: LiveData<Boolean>
        get() = _uploadPictureState

    private val _updateProfileState = MutableLiveData<Boolean>()

    val updateProfileState: LiveData<Boolean>
        get() = _updateProfileState

    private val _deleteState = MutableLiveData<Boolean>()

    val deleteState: LiveData<Boolean>
        get() = _deleteState

    private fun saveUser(user: User) = viewModelScope.launch {
        val gson = Gson()
        val json = gson.toJson(user)
        dataStoreRepository.saveUser(json)
    }

    private fun getProfile() = viewModelScope.launch {
        authRepository.getProfile().let {
            if (it.isSuccessful) {
                saveUser(it.body()!!)
            }
        }
    }

    fun deleteDeviceId(deviceId: String) = viewModelScope.launch {
        notificationRepository.deleteDeviceId(deviceId).let {
            _deleteState.postValue(true)
        }
    }

    fun updateProfile(user: User) = viewModelScope.launch {
        authRepository.updateProfile(user).let {
            _updateProfileState.postValue(it.isSuccessful)
            getProfile()
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
}