package org.m4xvel.weatherapp.data.repository

import org.m4xvel.weatherapp.data.remote.RemoteDataSource
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.data.util.toCityName
import org.m4xvel.weatherapp.data.util.toWeather
import org.m4xvel.weatherapp.db.WeatherDatabase
import org.m4xvel.weatherapp.domain.model.CityName
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

internal class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    database: WeatherDatabase
) : WeatherRepository {

    private val queries = database.weatherQueries

    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return remoteDataSource.getWeather(lat = lat, lon = lon)
            .toWeather()
    }

    override suspend fun getCityName(geoRequest: GeoRequest): CityName {
        return remoteDataSource.getCityName(geoRequest = geoRequest)
            .toCityName()
    }

    override suspend fun insertNote(weather: Weather, searchText: String) {
        queries.insertWeather(
            id = null,
            searchText = searchText,
            name = weather.name,
            temp = weather.temp,
            speed = weather.speed,
            humidity = weather.humidity,
            pressure = weather.pressure
        )
    }

    override suspend fun getAllWeather(city: String): List<org.m4xvel.weatherapp.db.Weather> {
        return queries.getAllWeather(searchText = city)
            .executeAsList()
            .map { it.toWeather() }
    }

    override suspend fun updateWeather(weather: Weather, searchText: String) {
        queries.updateWeather(
            searchText = searchText,
            name = weather.name,
            temp = weather.temp,
            speed = weather.speed,
            humidity = weather.humidity,
            pressure = weather.pressure
        )
    }
}
