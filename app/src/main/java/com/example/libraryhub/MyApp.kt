package com.example.libraryhub

import android.app.Application
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.utils.Constants
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Constants.APP_ID)
    }
}