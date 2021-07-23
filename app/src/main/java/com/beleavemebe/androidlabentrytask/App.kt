package com.beleavemebe.androidlabentrytask

import android.app.Application
import com.beleavemebe.androidlabentrytask.repositories.UserRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        UserRepository.initialize(this)
    }
}