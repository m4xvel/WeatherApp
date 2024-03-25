package org.m4xvel.weatherapp.data.util

import org.m4xvel.weatherapp.data.remote.geocoder.GeoResponse
import org.m4xvel.weatherapp.domain.model.LatAndLon

internal fun List<GeoResponse>.toCityName(): LatAndLon {

    val geoMappers = this.firstOrNull()

    return LatAndLon(
        lat = geoMappers?.lat ?: 0.0,
        lon = geoMappers?.lon ?: 0.0
    )
}