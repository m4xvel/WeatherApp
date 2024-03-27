package org.m4xvel.weatherapp.data.util

import org.m4xvel.weatherapp.data.remote.WeatherResponse
import org.m4xvel.weatherapp.domain.model.Weather

internal fun WeatherResponse.toWeather(): List<Weather> {

    return list.map { listItem ->
        Weather(
            name = city.name,
            temp = listItem.main.temp,
            speed = listItem.wind.speed,
            humidity = listItem.main.humidity,
            pressure = listItem.main.pressure,
            dt = listItem.dt
        )
    }
}