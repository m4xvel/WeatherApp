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
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import kotlin.math.roundToInt

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private lateinit var geoRequest: GeoRequest

    private val _state = MutableStateFlow(DataState())
    val state: StateFlow<DataState> = _state.asStateFlow()

    private var waitInput: Job? = null

    fun setSearchText(text: String) {
        _state.update { currentState ->
            currentState.copy(
                searchText = text,
                showButton = text.isNotEmpty(),
                loading = true
            )
        }

        if (text.isEmpty()) _state.update { it.copy(loading = false) }

        geoRequest = GeoRequest(text)

        waitInput?.cancel()

        waitInput = viewModelScope.launch {
            delay(600)
            setData()
        }
    }

    fun clearSearchText() {
        _state.update { currentState ->
            currentState.copy(
                searchText = "",
                showButton = false,
                loading = false
            )
        }
        waitInput?.cancel()
    }

    private fun setData() {
        viewModelScope.launch {
            try {
                val cityName = weatherRepository.getCityName(geoRequest)
                val weather = weatherRepository.getWeather(cityName.lat, cityName.lon)

                _state.update { currentState ->
                    currentState.copy(
                        city = weather.name,
                        temp = weather.temp.roundToInt(),
                        speed = weather.speed,
                        humidity = weather.humidity,
                        pressure = weather.pressure
                    )
                }
            } catch (e: Exception) {
                Log.d("MyTag", "Error: ${e.localizedMessage}")
            }
        }
        waitInput?.cancel()
    }
}