package org.m4xvel.weatherapp.ui

import org.m4xvel.weatherapp.domain.model.Weather

data class DataState(
    val city: String = "",
    val temp: Int = 0,
    val speed: Double = 0.0,
    val humidity: Long = 0,
    val pressure: Long = 0,

    val previousLat: Double? = null,
    val previousLon: Double? = null,

    val morningWeather: List<Weather>? = null,
    val duringTheDayWeather: List<Weather>? = null,
    val inTheEveningWeather: List<Weather>? = null,
    val nightWeather: List<Weather>? = null,

    val searchText: String = "",
    val previousSearchTextLength: Int = 0,
    val showButton: Boolean = false,
    val showAnimation: Boolean = true,
    val showCard: Boolean = false,
    val loading: Boolean = false,
)