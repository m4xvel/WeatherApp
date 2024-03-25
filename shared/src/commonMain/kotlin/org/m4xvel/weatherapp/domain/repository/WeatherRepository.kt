package org.m4xvel.weatherapp.domain.repository

import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.domain.model.LatAndLon
import org.m4xvel.weatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeather(lat: Double, lon: Double): List<Weather>

    suspend fun getLatAndLon(geoRequest: GeoRequest): LatAndLon

    suspend fun insertNote(weather: List<Weather>, searchText: String)

    suspend fun getAllWeather(city: String): List<org.m4xvel.weatherapp.db.Weather>

    suspend fun updateWeather(weather: List<Weather>, searchText: String)
}
