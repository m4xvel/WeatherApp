package org.m4xvel.weatherapp.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class WeatherService: WeatherClient() {

    suspend fun getWeather(weatherRequest: WeatherRequest): WeatherResponse = client.get {
        pathUrl("/weather")
        url {
            parameters.append("lat", weatherRequest.lat.toString())
            parameters.append("lon", weatherRequest.lon.toString())
        }
    }.body()
}
