package org.m4xvel.weatherapp.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

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
        }
    }
}