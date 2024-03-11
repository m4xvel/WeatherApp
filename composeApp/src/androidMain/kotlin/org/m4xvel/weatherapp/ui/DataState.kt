package org.m4xvel.weatherapp.ui

data class DataState(
    val city: String = "",
    val temp: Int = 0,
    val speed: Double = 0.0,
    val humidity: Long = 0,
    val pressure: Long = 0,
    val previousLat: Double? = null,
    val previousLon: Double? = null,
    val searchText: String = "",
    val previousSearchTextLength: Int = 0,
    val showButton: Boolean = false,
    val showCard: Boolean = false,
    val loading: Boolean = false,
)