package org.m4xvel.weatherapp.data.repository

import org.m4xvel.weatherapp.data.remote.RemoteDataSource
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.data.util.toCityName
import org.m4xvel.weatherapp.data.util.toWeather
import org.m4xvel.weatherapp.domain.model.CityName
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

internal class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return remoteDataSource.getWeather(lat = lat, lon = lon)
            .toWeather()
    }

    override suspend fun getCityName(geoRequest: GeoRequest): CityName {
        return remoteDataSource.getCityName(geoRequest = geoRequest)
            .toCityName()
    }
}
