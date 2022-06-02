package com.example.libraryhub.viewmodel

import androidx.lifecycle.*
import com.example.libraryhub.model.Package
import com.example.libraryhub.model.User
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.DataStoreRepository
import com.example.libraryhub.repository.PackageRepository
import com.example.libraryhub.utils.AppPreferences
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePaymentViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val packageRepository: PackageRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {
    val dataStoreUser = dataStoreRepository.readUser.asLiveData()
    val _buyState = MutableLiveData<Boolean>()
    private val _packages = MutableLiveData<List<Package>>()
    val packages : LiveData<List<Package>>
        get() = _packages
    val buyState : LiveData<Boolean>
        get() = _buyState

    init {
        getPackages()
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
            }
        }
    }

    private fun getPackages() = viewModelScope.launch {
        packageRepository.getPackages().let {
            if (it.isSuccessful) {
                _packages.postValue(it.body())
            }
        }
    }

    fun buyPackage(packageId: String) = viewModelScope.launch {
        packageRepository.buyPackage(packageId).let {
            if (it.isSuccessful) {
                _buyState.postValue(true)
                getProfile()
            } else {
                _buyState.postValue(false)
            }
        }
    }
}