package com.example.libraryhub.repository

import com.example.libraryhub.api.PackageAPI
import javax.inject.Inject

class PackageRepository @Inject constructor(private val packageAPI: PackageAPI) {

    suspend fun getPackages() = packageAPI.getPackages()

    suspend fun buyPackage(packageId: String) = packageAPI.buyPackage(packageId)
}