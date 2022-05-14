package com.example.libraryhub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryhub.model.Category
import com.example.libraryhub.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository): ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()

    val categories: LiveData<List<Category>>
        get() = _categories

    init {
        getAllRepository()
    }

    private fun getAllRepository() = viewModelScope.launch {
        categoryRepository.getAllCategory().let {
            if (it.isSuccessful) {
                _categories.postValue(it.body())
            }
        }
    }
}