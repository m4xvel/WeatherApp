package org.m4xvel.weatherapp.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.m4xvel.weatherapp.data.remote.WeatherRequest
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import kotlin.math.roundToInt

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val weatherRequest = WeatherRequest(53.12, 45.00)

    init {
        viewModelScope.launch {
            try {
                val weather = weatherRepository.getWeather(weatherRequest)
                Log.d("MyTag", "\n Город: ${weather.name}, " +
                        "\n Температура: ${weather.temp.roundToInt()},\n Скорость ветра: ${weather.speed}," +
                        "\n Влажность: ${weather.humidity},\n Давление: ${weather.pressure} ")
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