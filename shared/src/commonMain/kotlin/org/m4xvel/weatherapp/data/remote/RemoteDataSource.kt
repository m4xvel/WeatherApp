package org.m4xvel.weatherapp.data.remote

import kotlinx.coroutines.withContext
import org.m4xvel.weatherapp.util.Dispatcher

internal class RemoteDataSource(
    private val weatherService: WeatherService,
    private val dispatcher: Dispatcher
) {
    suspend fun getWeather(weatherRequest: WeatherRequest) = withContext(dispatcher.io) {
        weatherService.getWeather(weatherRequest = weatherRequest)
    }
}