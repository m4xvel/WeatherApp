package org.m4xvel.weatherapp.ui

data class DataState(
    val city: String = "",
    val temp: Int = 0,
    val speed: Double = 0.0,
    val humidity: Int = 0,
    val pressure: Int = 0,
    val searchText: String = "",
    val showButton: Boolean = false
)
