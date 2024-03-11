package org.m4xvel.weatherapp.db

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

internal fun createDatabase(driverFactory: DriverFactory): WeatherDatabase {
    val driver = driverFactory.createDriver()
    return WeatherDatabase(driver)
}