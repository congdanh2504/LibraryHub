package com.example.libraryhub

import android.app.Application
import com.example.libraryhub.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}