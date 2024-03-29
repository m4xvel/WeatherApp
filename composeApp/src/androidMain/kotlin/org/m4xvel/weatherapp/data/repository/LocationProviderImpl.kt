package org.m4xvel.weatherapp.data.repository

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

class LocationProviderImpl(
    private val context: Context
): LocationProvider {
    private var lastLocation: Location? = null

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10000).build()
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
        Log.d("!!!", "[init] $lastLocation")
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

    override fun getLastLocation(callback: (Location?) -> Unit) {
        if (lastLocation != null) {
            Log.d("!!!", "[if] $lastLocation")
            callback(lastLocation)
        } else {
            if (isLocationEnabled()) {
                requestLocationUpdate()
                Log.d("!!!", "[else] $lastLocation")
                Log.d("!!!", "[locationResult] ${locationCallback}")
                Log.d("!!!", "[locationRequest] ${locationRequest}")
                callback(lastLocation)
            } else {
                Toast.makeText(context, "Включите местоположение!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}