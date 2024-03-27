package org.m4xvel.weatherapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponse(
    val list: List<ListApi>,
    val city: City
)

@Serializable
data class ListApi(
    val wind: Wind,
    val main: Main,
    val dt: Long,
    @SerialName("dt_txt")
    val dtTxt: String
)

@Serializable
data class City(
    val name: String
)

@Serializable
data class Wind(
    val speed: Double
)

@Serializable
data class Main(
    val temp: Double,
    val humidity: Long,
    val pressure: Long
)
