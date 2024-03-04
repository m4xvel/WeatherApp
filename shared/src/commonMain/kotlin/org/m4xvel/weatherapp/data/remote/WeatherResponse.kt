package org.m4xvel.weatherapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
internal data class WeatherResponse(
    val name: String,
    val wind: Wind,
    val main: Main
)

@Serializable
data class Wind(
    val speed: Double
)

@Serializable
data class Main (
    val temp: Double,
    val humidity: Int,
    val pressure: Int
)
