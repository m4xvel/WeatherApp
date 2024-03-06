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
import org.m4xvel.weatherapp.model.DataState
import org.m4xvel.weatherapp.model.GeoState
import kotlin.math.roundToInt

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private lateinit var geoRequest: GeoRequest

    private val _state = MutableStateFlow(DataState())
    val state: StateFlow<DataState> = _state.asStateFlow()

    private var waitInput: Job? = null

    private val _geoState = MutableStateFlow(GeoState())
    val geoState: StateFlow<GeoState> = _geoState.asStateFlow()

    fun setSearchText(text: String) {
        _state.update { currentState ->
            currentState.copy(
                searchText = text,
                showButton = text.isNotEmpty(),
            )
        }

        if (text.isEmpty()) _state.update { it.copy(loading = false) }

        geoRequest = GeoRequest(text)

        isLoadingAndSetData()
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

    fun isLoadingAndSetData() {
        waitInput?.cancel()

        waitInput = viewModelScope.launch {
            isLoading(true)
            delay(650)
            setData()
        }
    }

    private fun isLoading(value: Boolean) {
        if(value) {
            _state.update { currentState ->
                currentState.copy(
                    loading = true
                )
            }
        } else {
            _state.update { currentState ->
                currentState.copy(
                    loading = false,
                    showCard = true
                )
            }
        }
    }

    private fun setData() {
        viewModelScope.launch {
            try {
                val cityName = weatherRepository.getCityName(geoRequest)
                val weather = weatherRepository.getWeather(geoState.value.lat!!, geoState.value.lon!!)

                _state.update { currentState ->
                    currentState.copy(
                        city = weather.name,
                        temp = weather.temp.roundToInt(),
                        speed = weather.speed,
                        humidity = weather.humidity,
                        pressure = weather.pressure,
                    )
                }
                isLoading(false)
            } catch (e: Exception) {
                _state.update { it.copy( loading = false ) }
                Log.d("MyTag", "Error: ${e.localizedMessage}")
            }
        }
    }
}