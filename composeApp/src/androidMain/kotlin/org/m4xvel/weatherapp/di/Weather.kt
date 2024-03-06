package org.m4xvel.weatherapp.di

import android.app.Application
import org.koin.core.context.startKoin

class Weather: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(getSharedModule().plus(appModule))
        }
    }
}