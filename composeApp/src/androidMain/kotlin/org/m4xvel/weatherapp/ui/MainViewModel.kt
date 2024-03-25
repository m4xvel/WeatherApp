package org.m4xvel.weatherapp.ui

import android.annotation.SuppressLint
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
import org.m4xvel.weatherapp.data.repository.LocationProvider
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.domain.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

@SuppressLint("SimpleDateFormat")
class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private lateinit var geoRequest: GeoRequest

    private val _state = MutableStateFlow(DataState())
    val state: StateFlow<DataState> = _state.asStateFlow()

    private val _stateList = MutableStateFlow(mutableListOf<Double>())
    val stateList: StateFlow<MutableList<Double>> = _stateList.asStateFlow()

    private var waitInput: Job? = null

    fun setSearchText(text: String) {
        _state.update { currentState ->
            currentState.copy(
                searchText = text,
                showButton = text.isNotEmpty()
            )
        }

        if (text.length > 3 && text.length > _state.value.previousSearchTextLength) {

            _state.update { it.copy(previousSearchTextLength = text.length) }

            if (text.isEmpty()) isLoading(false)

            geoRequest = GeoRequest(text)

            waitInput?.cancel()

            waitInput = viewModelScope.launch {
                isLoading(true)
                delay(650)
                setDataApi(searchText = text)
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
        locationProvider.getLastLocation { lastLocation ->

            val lat = lastLocation?.latitude
            val lon = lastLocation?.longitude

            if (lat != _state.value.previousLat && lat != null) {
                isLoading(true)
                viewModelScope.launch {
                    val weather = weatherRepository.getWeather(
                        lastLocation.latitude,
                        lastLocation.longitude
                    )
                    val currentWeather: List<Weather> = listOf(weather.first())
                    getDataTime(weather = weather)
                    currentWeather.map { listItem ->
                        _state.update {
                            it.copy(
                                city = listItem.name,
                                temp = listItem.temp.roundToInt(),
                                speed = listItem.speed,
                                humidity = listItem.humidity,
                                pressure = listItem.pressure,
                                previousLat = lat,
                                previousLon = lon,
                                searchText = ""
                            )
                        }
                    }
                }
                isLoading(false)
            }
        }
    }

    private fun setDataApi(searchText: String) {
        viewModelScope.launch {
            val allWeather = weatherRepository.getAllWeather(searchText)
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
        }
        viewModelScope.launch {
            val allWeather = weatherRepository.getAllWeather(searchText)
            isLoading(true)
            delay(500L)
            val cityName = weatherRepository.getLatAndLon(geoRequest)
            val weather = weatherRepository.getWeather(cityName.lat, cityName.lon)
            val currentWeather: List<Weather> = listOf(weather.first())
            getDataTime(weather = weather)
            if (allWeather.isEmpty()) {
                weatherRepository.insertNote(currentWeather, searchText)
            } else {
                weatherRepository.updateWeather(currentWeather, searchText)
            }
            currentWeather.map { listItem ->
                Log.d("GetWeather", "$listItem")
                _state.update {
                    it.copy(
                        city = listItem.name,
                        temp = listItem.temp.roundToInt(),
                        speed = listItem.speed,
                        humidity = listItem.humidity,
                        pressure = listItem.pressure,
                        previousLat = null,
                        previousLon = null,
                        searchText = ""
                    )
                }
            }
            isLoading(false)
        }
    }

    private fun getDataTime(day: Int = 0, weather: List<Weather>) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, day)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val data = sdf.format(calendar.time)

        _state.update { it ->
            it.copy(
                morningWeather = weather.filter { it.time == "$data 09:00:00" },
                duringTheDayWeather = weather.filter { it.time == "$data 12:00:00" },
                inTheEveningWeather = weather.filter { it.time == "$data 15:00:00" },
                nightWeather = weather.filter { it.time == "$data 18:00:00" }
            )
        }
        Log.d(
            "GetWeather",
            "${state.value.morningWeather} \n${state.value.duringTheDayWeather} \n${state.value.inTheEveningWeather} \n${state.value.nightWeather}"
        )
    }

    fun isPlayingAnimation(value: Boolean) {
        _state.update { it.copy(showAnimation = value) }
    }
}