package org.m4xvel.weatherapp.domain.model

data class Weather(
    val name: String,
    val temp: Double,
    val speed: Double,
    val humidity: Int,
    val pressure: Int
)
