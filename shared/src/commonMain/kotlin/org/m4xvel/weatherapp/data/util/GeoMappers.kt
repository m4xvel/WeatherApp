package org.m4xvel.weatherapp.data.util

import org.m4xvel.weatherapp.data.remote.geocoder.GeoResponse
import org.m4xvel.weatherapp.domain.model.CityName

internal fun GeoResponse.toCityName(): CityName {
    return CityName(
        lat = lat,
        lon = lon
    )
}