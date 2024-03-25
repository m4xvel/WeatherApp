package org.m4xvel.weatherapp.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.data.remote.geocoder.GeoResponse

private const val API_KEY = "22599d210f9ff6005c96992af90fd829"

internal class WeatherService : WeatherClient() {

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse = client.get {
        pathUrl("/forecast")
        url {
            parameters.append("lat", lat.toString())
            parameters.append("lon", lon.toString())
            parameters.append("appid", API_KEY)
            parameters.append("units", "metric")
        }
    }.body()

    suspend fun getLatAndLon(geoRequest: GeoRequest): List<GeoResponse> = client.get {
        pathUrlForName("/direct")
        url {
            parameters.append("q", geoRequest.city)
            parameters.append("appid", API_KEY)
        }
    }.body()
}
