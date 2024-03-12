package org.m4xvel.weatherapp.ui

import android.content.Context
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import kotlin.math.roundToInt

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private lateinit var geoRequest: GeoRequest

    private val _state = MutableStateFlow(DataState())
    val state: StateFlow<DataState> = _state.asStateFlow()

    private var waitInput: Job? = null

    init {
        viewModelScope.launch {
            val allWeather = weatherRepository.getAllWeather()
            for (item in allWeather) {
                try {
                    _state.update {
                        it.copy(
                            city = item.name,
                            temp = item.temp.roundToInt(),
                            speed = item.speed,
                            humidity = item.humidity,
                            pressure = item.pressure,
                            showCard = true
                        )
                    }
                } finally {
                    weatherRepository.deleteAllWeather()
                    Log.d("MyTag", "$allWeather")
                }
            }
        }
    }

    fun setSearchText(text: String) {
        _state.update { currentState ->
            currentState.copy(
                searchText = text,
                showButton = text.isNotEmpty()
            )
        }

        if (text.length > 3 && text.length > _state.value.previousSearchTextLength) {

            _state.update { it.copy(previousSearchTextLength = text.length) }

            if (text.isEmpty()) _state.update { it.copy(loading = false) }

            geoRequest = GeoRequest(text)

            waitInput?.cancel()

            waitInput = viewModelScope.launch {
                isLoading(true)
                delay(650)
                setDataApi()
            }
        }

        if (text.length < _state.value.previousSearchTextLength) {
            _state.update { it.copy(previousSearchTextLength = text.length) }
        }
    }


    fun clearSearchText() {
        _state.update { currentState ->
            currentState.copy(
                searchText = "",
                showButton = false,
            )
        }
        waitInput?.cancel()
    }

    private fun isLoading(value: Boolean) {
        if (value) {
            _state.update {
                it.copy(
                    loading = true
                )
            }
        } else {
            _state.update {
                it.copy(
                    loading = false,
                    showCard = true
                )
            }
        }
    }

    fun setDataLocation(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest = LocationRequest.Builder(1).build()
        Log.d("MyTag", "$locationRequest")
        try {
            if (isLocationEnabled(context)) {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            for (location in locationResult.locations) {
                                if (location.latitude != _state.value.previousLat && location.longitude != _state.value.previousLon) {
                                    _state.update { it.copy(loading = true) }
                                    viewModelScope.launch {
                                        try {
                                            val weather = weatherRepository.getWeather(
                                                location.latitude,
                                                location.longitude
                                            )
                                            weatherRepository.insertNote(weather)
                                            val allWeather = weatherRepository.getAllWeather()

                                            for (item in allWeather) {
                                                _state.update {
                                                    it.copy(
                                                        city = item.name,
                                                        temp = item.temp.roundToInt(),
                                                        speed = item.speed,
                                                        humidity = item.humidity,
                                                        pressure = item.pressure,
                                                        previousLat = location.latitude,
                                                        previousLon = location.longitude,
                                                        searchText = ""
                                                    )
                                                }
                                            }
                                            isLoading(false)
                                        } catch (e: Exception) {
                                            _state.update { it.copy(loading = false) }
                                            Log.d("MyTag", "Error: ${e.localizedMessage}")
                                        }
                                    }
                                }
                            }
                        }
                    },
                    Looper.getMainLooper()
                )
            } else {
                Toast.makeText(context, "Включите местоположение!", Toast.LENGTH_SHORT).show()
            }

        } catch (e: SecurityException) {
            Log.d("MyTag", "Error: ${e.localizedMessage}")
        }
    }

    private fun setDataApi() {
        viewModelScope.launch {
            try {
                val cityName = weatherRepository.getCityName(geoRequest)
                val weather = weatherRepository.getWeather(cityName.lat, cityName.lon)
                weatherRepository.insertNote(weather)
                val allWeather = weatherRepository.getAllWeather()

                for (item in allWeather) {
                    _state.update {
                        it.copy(
                            city = item.name,
                            temp = item.temp.roundToInt(),
                            speed = item.speed,
                            humidity = item.humidity,
                            pressure = item.pressure,
                            previousLat = null,
                            previousLon = null
                        )
                    }
                }
                isLoading(false)
            } catch (e: Exception) {
                _state.update { it.copy(loading = false) }
                Log.d("MyTag", "Error: ${e.localizedMessage}")
            }
        }
    }

    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}