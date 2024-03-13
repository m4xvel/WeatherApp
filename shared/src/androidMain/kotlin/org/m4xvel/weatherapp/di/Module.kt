package org.m4xvel.weatherapp.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.m4xvel.weatherapp.db.WeatherDatabase

val module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            WeatherDatabase.Schema,
            androidContext(),
            "weather.db"
        )
    }
}