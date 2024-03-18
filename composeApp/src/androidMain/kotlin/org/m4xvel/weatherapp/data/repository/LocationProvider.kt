package org.m4xvel.weatherapp.data.repository

import android.location.Location

interface LocationProvider {
    fun getLastLocation(callback: (Location?) -> Unit)
}