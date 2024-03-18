package org.m4xvel.weatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.data.repository.LocationProviderImpl
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import kotlin.math.roundToInt

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProviderImpl: LocationProviderImpl
) : ViewModel() {

    private lateinit var geoRequest: GeoRequest

    private val _state = MutableStateFlow(DataState())
    val state: StateFlow<DataState> = _state.asStateFlow()

    private var waitInput: Job? = null

    init {
        viewModelScope.launch {
            val allWeather = weatherRepository.getAllWeather()
            for (item in allWeather) {
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

    fun setDataLocation() {
        locationProviderImpl.getLastLocation()
    }

    private fun setDataApi() {
        viewModelScope.launch {
            try {
                val cityName = weatherRepository.getCityName(geoRequest)
                val weather = weatherRepository.getWeather(cityName.lat, cityName.lon)
                weatherRepository.insertNote(weather)
                val allWeather = weatherRepository.getAllWeather()
                Log.d("MyTag", "$allWeather")

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
}