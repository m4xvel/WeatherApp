package org.m4xvel.weatherapp.domain.repository

import org.m4xvel.weatherapp.data.remote.WeatherRequest
import org.m4xvel.weatherapp.domain.model.Weather

internal interface WeatherRepository {

    suspend fun getWeather(weatherRequest: WeatherRequest): Weather
}
