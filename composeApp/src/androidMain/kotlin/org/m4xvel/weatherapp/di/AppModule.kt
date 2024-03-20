package org.m4xvel.weatherapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.m4xvel.weatherapp.data.repository.LocationProvider
import org.m4xvel.weatherapp.data.repository.LocationProviderImpl
import org.m4xvel.weatherapp.ui.MainViewModel

val appModule = module {
    single<LocationProvider> { LocationProviderImpl(androidContext()) }
    viewModel { MainViewModel(get(), get()) }
}