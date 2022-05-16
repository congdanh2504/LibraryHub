package com.example.libraryhub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Package
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.PackageRepository
import com.example.libraryhub.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val packageRepository: PackageRepository
): ViewModel() {
    val _buyState = MutableLiveData<Boolean>()
    private val _packages = MutableLiveData<List<Package>>()
    val packages : LiveData<List<Package>>
        get() = _packages
    val buyState : LiveData<Boolean>
        get() = _buyState

    init {
        getPackages()
    }

    private fun getProfile() = viewModelScope.launch {
        authRepository.getProfile().let {
            if (it.isSuccessful) {
                AppPreferences.user = it.body()
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