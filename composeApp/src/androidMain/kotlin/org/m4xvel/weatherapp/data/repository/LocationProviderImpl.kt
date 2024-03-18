package org.m4xvel.weatherapp.data.repository

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

class LocationProviderImpl(
    private val weatherRepository: WeatherRepository,
    private val context: Context
) {
    // последняя локация, отменяет обновление локации при ее получении
    private var lastLocation: Location? = null

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.Builder(1000L).build()
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.lastOrNull()?.let {
                lastLocation = it
                removeLocationUpdate()
            }
        }
    }

    init {
        requestLocationUpdate()
    }

    private fun requestLocationUpdate() {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Log.d("MyTag", "Error: ${e.localizedMessage}")
        }
    }

    private fun removeLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun getLastLocation(): Location? {
        Log.d("!!!", "$lastLocation")
        return lastLocation
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}