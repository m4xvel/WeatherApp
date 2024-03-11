package org.m4xvel.weatherapp.di

import app.cash.sqldelight.db.SqlDriver
import org.koin.dsl.module
import org.m4xvel.weatherapp.data.remote.RemoteDataSource
import org.m4xvel.weatherapp.data.remote.WeatherService
import org.m4xvel.weatherapp.data.repository.WeatherRepositoryImpl
import org.m4xvel.weatherapp.db.WeatherDatabase
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import org.m4xvel.weatherapp.util.provideDispatcher

private val dataModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { WeatherService() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}

private val databaseModule = module {
    single { WeatherDatabase(get()) }
}

private val sharedModule = listOf(domainModule, dataModule, utilityModule)

fun getSharedModule() = sharedModule