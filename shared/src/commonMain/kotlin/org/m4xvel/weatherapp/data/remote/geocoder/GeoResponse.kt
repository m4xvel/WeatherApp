package org.m4xvel.weatherapp.data.remote.geocoder

import kotlinx.serialization.Serializable

@Serializable
internal data class GeoResponse(
    val lat: Double,
    val lon: Double
)

