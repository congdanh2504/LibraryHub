package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _checkQuantityState = MutableLiveData<Boolean>()

    val checkQuantityState: LiveData<Boolean>
        get() = _checkQuantityState

    private val _borrowState = MutableLiveData<Boolean>()

    val borrowState: LiveData<Boolean>
        get() = _borrowState

    private fun getProfile() = viewModelScope.launch {
        authRepository.getProfile().let {
            if (it.isSuccessful) {
                AppPreferences.user = it.body()
            }
        }
    }

    fun borrowBook(books: List<CartBook>) = viewModelScope.launch {
        bookRepository.checkQuantity(books).let {
            if (it.body() == false) {
                _checkQuantityState.postValue(false)
            } else {
                bookRepository.borrowBook(books).let { it2 ->
                    if (it2.isSuccessful) {
                        getProfile()
                        _borrowState.postValue(true)
                    } else {
                        _borrowState.postValue(false)
                    }
                }
            }
        }
    }

//    fun borrowBook(books: List<CartBook>) = viewModelScope.launch {
//        bookRepository.borrowBook(books).let {
//            if (it.isSuccessful) {
//                getProfile()
//                _borrowState.postValue(true)
//            } else {
//                _borrowState.postValue(false)
//            }
//        }
//    }
}