package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.libraryhub.model.AuthUser
import com.example.libraryhub.model.User
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.DataStoreRepository
import com.example.libraryhub.repository.NotificationRepository
import com.example.libraryhub.utils.AppPreferences
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val notificationRepository: NotificationRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _loginState = MutableLiveData<Boolean>()

    val loginState: LiveData<Boolean>
        get() = _loginState
    val user: LiveData<User>
        get() = _user

    init {
        getProfile()
    }

    private fun saveUser(user: User) = viewModelScope.launch {
        val gson = Gson()
        val json = gson.toJson(user)
        dataStoreRepository.saveUser(json)
    }

    private fun getProfile() = viewModelScope.launch {
        authRepository.getProfile().let {
            if (it.isSuccessful) {
                saveUser(it.body()!!)
                _user.postValue(it.body())
            }
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        authRepository.signIn(AuthUser(email = email, password = password)).let {
            if (it.isSuccessful) {
                AppPreferences.JWT = it.body()
                _loginState.postValue(true)
                getProfile()
            } else {
                _loginState.postValue(false)
            }
        }
    }

    fun signInWithGoogle(idToken: String) = viewModelScope.launch {
        authRepository.signInWithGoogle(idToken).let {
            if (it.isSuccessful) {
                AppPreferences.JWT = it.body()
                _loginState.postValue(true)
                getProfile()
            } else {
                _loginState.postValue(false)
            }
        }
    }

    fun addDeviceId(deviceId: String) = viewModelScope.launch {
        notificationRepository.addDeviceId(deviceId)
    }
}