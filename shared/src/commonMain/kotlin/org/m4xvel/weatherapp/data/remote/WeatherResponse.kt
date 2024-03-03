package org.m4xvel.weatherapp.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class WeatherResponse(
    val name: String,
    val temp: String,
    val speed: String,
    val humidity: String,
    val pressure: String
)
