package org.m4xvel.weatherapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.m4xvel.weatherapp.ui.MainViewModel

val appModule = module {
    viewModel { MainViewModel(get()) }
}