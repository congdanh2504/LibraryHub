package com.example.libraryhub.repository

import com.example.libraryhub.api.CategoryAPI
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryAPI: CategoryAPI) {

    suspend fun getAllCategory() = categoryAPI.getAllCategories()
}