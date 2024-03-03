package org.m4xvel.weatherapp.usecase

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.m4xvel.weatherapp.data.remote.WeatherRequest
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

class GetWeatherUseCase: KoinComponent {
    private val repository: WeatherRepository by inject()

    @Throws(Exception::class)
    suspend operator fun invoke(weatherRequest: WeatherRequest): Weather {
        return repository.getWeather(weatherRequest = weatherRequest)
    }
}