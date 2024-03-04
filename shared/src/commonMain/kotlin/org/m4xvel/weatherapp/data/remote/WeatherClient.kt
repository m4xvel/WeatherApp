package org.m4xvel.weatherapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val API_KEY = "22599d210f9ff6005c96992af90fd829"

internal abstract class WeatherClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = false
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
    fun HttpRequestBuilder.pathUrl(path: String) {
        url {
            protocol = URLProtocol.HTTPS
            host = "api.openweathermap.org/data/2.5"
            path(path)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }
    }

    fun HttpRequestBuilder.pathUrlForName(path: String) {
        url {
            protocol = URLProtocol.HTTPS
            host = "api.openweathermap.org/geo/1.0"
            path(path)
            parameter("appid", API_KEY)
        }
    }
}