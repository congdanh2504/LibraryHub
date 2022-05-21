package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.AuthUser
import com.example.libraryhub.model.User
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.NotificationRepository
import com.example.libraryhub.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository, private val notificationRepository: NotificationRepository): ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _loginState = MutableLiveData<Boolean>()

    val loginState: LiveData<Boolean>
        get() = _loginState
    val user: LiveData<User>
        get() = _user

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        authRepository.getProfile().let {
            if (it.isSuccessful) {
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