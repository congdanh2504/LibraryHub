package com.example.libraryhub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.libraryhub.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository): ViewModel() {

    val dataStoreUser = dataStoreRepository.readUser.asLiveData()
}