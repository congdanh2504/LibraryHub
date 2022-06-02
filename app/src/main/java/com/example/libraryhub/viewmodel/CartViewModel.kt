package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.User
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.BookRepository
import com.example.libraryhub.repository.DataStoreRepository
import com.example.libraryhub.utils.AppPreferences
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val dataStoreUser = dataStoreRepository.readUser.asLiveData()

    private val _checkQuantityState = MutableLiveData<Boolean>()

    val checkQuantityState: LiveData<Boolean>
        get() = _checkQuantityState

    private val _borrowState = MutableLiveData<Boolean>()

    val borrowState: LiveData<Boolean>
        get() = _borrowState

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
}