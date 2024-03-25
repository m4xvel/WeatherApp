package org.m4xvel.weatherapp.data.remote

import kotlinx.coroutines.withContext
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.util.Dispatcher

internal class RemoteDataSource(
    private val weatherService: WeatherService,
    private val dispatcher: Dispatcher
) {
    suspend fun getWeather(lat: Double, lon: Double) = withContext(dispatcher.io) {
        weatherService.getWeather(lat = lat, lon = lon)
    }

    suspend fun getLatAndLon(geoRequest: GeoRequest) = withContext(dispatcher.io) {
        weatherService.getLatAndLon(geoRequest = geoRequest)
    }
}