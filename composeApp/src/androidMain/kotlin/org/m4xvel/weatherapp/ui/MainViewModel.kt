package org.m4xvel.weatherapp.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.m4xvel.weatherapp.data.remote.WeatherRequest
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import kotlin.math.roundToInt

data class WeatherState(
    val city: String? = null,
    val temp: Int? = null
) {
    val isLoading: Boolean
        get() = city == null && temp == null


}

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val weatherRequest = WeatherRequest(53.12, 45.00)

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    private val _city = mutableStateOf("")
    val city: State<String> = _city

    private val _temp = mutableStateOf(0)
    val temp: State<Int> = _temp

    private val _speed = mutableStateOf(0.0)
    val speed: State<Double> = _speed

    private val _humidity = mutableStateOf(0)
    val humidity: State<Int> = _humidity

    private val _pressure = mutableStateOf(0)
    val pressure: State<Int> = _pressure

    init {
        viewModelScope.launch {
            try {
                val weather = weatherRepository.getWeather(weatherRequest)
                Log.d("MyTag", "\n Город: ${weather.name}, " +
                        "\n Температура: ${weather.temp.roundToInt()},\n Скорость ветра: ${weather.speed}," +
                        "\n Влажность: ${weather.humidity},\n Давление: ${weather.pressure} ")

                _state.update {
                    it.copy(
                        city = weather.name,

                    )
                }

                _city.value = weather.name
                _temp.value = weather.temp.roundToInt()
                _speed.value = weather.speed
                _humidity.value = weather.humidity
                _pressure.value = weather.pressure
            } catch (e: Exception) {
                Log.d("MyTag", "Error: ${e.localizedMessage}")
            }
        }
    }

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _showButton = mutableStateOf(false)
    val showButton: State<Boolean> = _showButton

    fun setSearchText(text: String) {
        _searchText.value = text
        _showButton.value = text.isNotEmpty()
    }

    fun clearSearchText() {
        _searchText.value = ""
        _showButton.value = false
    }
}