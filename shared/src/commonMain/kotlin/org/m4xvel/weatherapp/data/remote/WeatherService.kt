package org.m4xvel.weatherapp.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val API_KEY = "22599d210f9ff6005c96992af90fd829"
internal class WeatherService: WeatherClient() {

    suspend fun getWeather(weatherRequest: WeatherRequest): WeatherResponse = client.get {
        pathUrl("/weather")
        url {
            parameters.append("lat", weatherRequest.lat.toString())
            parameters.append("lon", weatherRequest.lon.toString())
        }
        parameter("appid", API_KEY)
    }.body()
}
