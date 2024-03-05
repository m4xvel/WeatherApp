package org.m4xvel.weatherapp.data.util

import org.m4xvel.weatherapp.data.remote.WeatherResponse
import org.m4xvel.weatherapp.domain.model.Weather

internal fun WeatherResponse.toWeather(): Weather {

    return Weather(
        name = name,
        temp = main.temp,
        speed = wind.speed,
        humidity = main.humidity,
        pressure = main.pressure
    )
}