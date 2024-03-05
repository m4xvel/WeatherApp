package org.m4xvel.weatherapp.data.util

import org.m4xvel.weatherapp.data.remote.geocoder.GeoResponse
import org.m4xvel.weatherapp.domain.model.CityName

internal fun List<GeoResponse>.toCityName(): CityName {

    val geoMappers = this.firstOrNull()

    return CityName(
        lat = geoMappers?.lat ?: 0.0,
        lon = geoMappers?.lon ?: 0.0
    )
}