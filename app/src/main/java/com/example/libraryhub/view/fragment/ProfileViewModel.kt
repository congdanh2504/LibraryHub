package com.example.libraryhub.view.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Package
import com.example.libraryhub.repository.AuthRepository
import com.example.libraryhub.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val packageRepository: PackageRepository
): ViewModel() {

    private val _packages = MutableLiveData<List<Package>>()
    val packages : LiveData<List<Package>>
        get() = _packages

    init {
        getPackages()
    }

    private fun getPackages() = viewModelScope.launch {
        packageRepository.getPackages().let {
            if (it.isSuccessful) {
                _packages.postValue(it.body())
            }
        }
    }
}