package org.m4xvel.weatherapp.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import kotlin.math.roundToInt

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val geoRequest = GeoRequest("Penza")

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
                val weather = weatherRepository.getWeather(53.200001,45.0)
                Log.d("MyTag", "\n Город: ${weather.name}, " +
                        "\n Температура: ${weather.temp.roundToInt()},\n Скорость ветра: ${weather.speed}," +
                        "\n Влажность: ${weather.humidity},\n Давление: ${weather.pressure} ")
                _city.value = weather.name
                _temp.value = weather.temp.roundToInt()
                _speed.value = weather.speed
                _humidity.value = weather.humidity
                _pressure.value = weather.pressure
                val cityName = weatherRepository.getCityName(geoRequest)
                Log.d("MyTag", "${cityName.lat}, ${cityName.lon}")
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