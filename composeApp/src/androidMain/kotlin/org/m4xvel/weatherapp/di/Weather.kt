package org.m4xvel.weatherapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Weather: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Weather)
            modules(getSharedModule() + appModule + module)
        }
    }
}