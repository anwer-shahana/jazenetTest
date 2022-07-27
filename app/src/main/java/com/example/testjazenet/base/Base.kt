package com.example.testjazenet.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.testjazenet.utils.SessionManager
import timber.log.Timber

class Base: Application() {

    override fun onCreate() {
        super.onCreate()
        SessionManager.initializeContext(applicationContext)
        Timber.plant(Timber.DebugTree())

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }


}