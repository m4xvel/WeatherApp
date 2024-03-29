package org.m4xvel.weatherapp.data.util

import org.m4xvel.weatherapp.db.Weather

fun Weather.toWeather(): Weather {
    return Weather(
        id = id,
        searchText = searchText,
        name = name,
        temp = temp,
        speed = speed,
        humidity = humidity,
        pressure = pressure
    )
}