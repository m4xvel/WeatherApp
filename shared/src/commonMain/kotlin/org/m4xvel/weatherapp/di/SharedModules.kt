package org.m4xvel.weatherapp.di

import org.koin.dsl.module
import org.m4xvel.weatherapp.data.remote.RemoteDataSource
import org.m4xvel.weatherapp.data.remote.WeatherService
import org.m4xvel.weatherapp.data.repository.WeatherRepositoryImpl
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import org.m4xvel.weatherapp.usecase.GetWeatherUseCase
import org.m4xvel.weatherapp.util.provideDispatcher

private val dataModule = module {
    factory { RemoteDataSource(get(), get()) }
    factory { WeatherService() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val domainModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    factory { GetWeatherUseCase() }
}

private val sharedModule = listOf(domainModule, dataModule, utilityModule)

fun getSharedModule() = sharedModule