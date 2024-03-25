package org.m4xvel.weatherapp.data.repository

import org.m4xvel.weatherapp.data.remote.RemoteDataSource
import org.m4xvel.weatherapp.data.remote.geocoder.GeoRequest
import org.m4xvel.weatherapp.data.util.toCityName
import org.m4xvel.weatherapp.data.util.toWeather
import org.m4xvel.weatherapp.db.WeatherDatabase
import org.m4xvel.weatherapp.domain.model.LatAndLon
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.domain.repository.WeatherRepository

internal class WeatherRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    database: WeatherDatabase
) : WeatherRepository {

    private val queries = database.weatherQueries

    override suspend fun getWeather(lat: Double, lon: Double): List<Weather> {
        return remoteDataSource.getWeather(lat = lat, lon = lon)
            .toWeather()
    }

    override suspend fun getLatAndLon(geoRequest: GeoRequest): LatAndLon {
        return remoteDataSource.getLatAndLon(geoRequest = geoRequest)
            .toCityName()
    }

    override suspend fun insertNote(weather: List<Weather>, searchText: String) {
        weather.map {
            queries.insertWeather(
                id = null,
                searchText = searchText,
                name = it.name,
                temp = it.temp,
                speed = it.speed,
                humidity = it.humidity,
                pressure = it.pressure
            )
        }
    }

    override suspend fun getAllWeather(city: String): List<org.m4xvel.weatherapp.db.Weather> {
        return queries.getAllWeather(searchText = city)
            .executeAsList()
            .map { it.toWeather() }
    }

    override suspend fun updateWeather(weather: List<Weather>, searchText: String) {
        weather.map {
            queries.updateWeather(
                searchText = searchText,
                name = it.name,
                temp = it.temp,
                speed = it.speed,
                humidity = it.humidity,
                pressure = it.pressure
            )
        }
    }
}
