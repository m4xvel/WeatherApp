package org.m4xvel.weatherapp.data.remote

import io.github.aakira.napier.Napier
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
import org.m4xvel.weatherapp.util.initLogger


internal abstract class WeatherClient {
    val client = HttpClient {
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HTTP Client", message = message)
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = false
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }.also { initLogger() }
    fun HttpRequestBuilder.pathUrl(path: String) {
        url {
            protocol = URLProtocol.HTTPS
            host = "api.openweathermap.org/data/2.5"
            path(path)
        }
    }

    fun HttpRequestBuilder.pathUrlForName(path: String) {
        url {
            protocol = URLProtocol.HTTPS
            host = "api.openweathermap.org/geo/1.0"
            path(path)
        }
    }
}