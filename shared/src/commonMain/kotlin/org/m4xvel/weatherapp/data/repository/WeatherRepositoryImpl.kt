package org.m4xvel.weatherapp.data.repository

import org.m4xvel.weatherapp.data.remote.RemoteDataSource
import org.m4xvel.weatherapp.data.remote.WeatherRequest
import org.m4xvel.weatherapp.data.util.toWeather
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

internal class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {
    override suspend fun getWeather(weatherRequest: WeatherRequest): Weather {
        return remoteDataSource.getWeather(weatherRequest = weatherRequest).toWeather()
    }
}