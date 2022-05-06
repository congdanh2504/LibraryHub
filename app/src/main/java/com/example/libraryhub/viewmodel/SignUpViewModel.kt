package com.example.libraryhub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.AuthUser
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _signUpState = MutableLiveData<Boolean>()

    val signUpState: LiveData<Boolean>
        get() = _signUpState

    fun signUp(username: String, email: String, password: String) = viewModelScope.launch {
        authRepository.signUp(AuthUser(username = username,email = email, password = password)).let {
            if (it.isSuccessful) {
                _signUpState.postValue(true)
            } else {
                _signUpState.postValue(false)
            }
        }
    }
}