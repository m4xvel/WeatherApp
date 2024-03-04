package org.m4xvel.weatherapp.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.m4xvel.weatherapp.di.appModule
import org.m4xvel.weatherapp.di.getSharedModule

class Weather: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(getSharedModule().plus(appModule))
        }
    }
}