package org.m4xvel.weatherapp.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.get
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.data.remote.geocoder.GeoResponse

internal class WeatherService: WeatherClient() {

    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse = client.get {
        pathUrl("/weather")
        url {
            parameters.append("lat", lat.toString())
            parameters.append("lon", lon.toString())
        }
    }.body()

    suspend fun getCityName(geoRequest: GeoRequest): GeoResponse = client.get {
        pathUrlForName("/direct")
        url {
            parameters.append("q", geoRequest.city)
        }
    }.body()
}
