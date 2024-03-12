package org.m4xvel.weatherapp.domain.repository

import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.domain.model.CityName
import org.m4xvel.weatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeather(lat: Double, lon: Double): Weather

    suspend fun getCityName(geoRequest: GeoRequest): CityName

    suspend fun insertNote(weather: Weather)

    suspend fun getAllWeather(): List<org.m4xvel.weatherapp.db.Weather>

    suspend fun deleteAllWeather()
}
