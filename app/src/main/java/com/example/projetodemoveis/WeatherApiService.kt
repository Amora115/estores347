package com.example.projetodemoveis

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(@Query("city") city: String): WeatherResponse
}

data class WeatherResponse(
    val temperature: Double,
    val description: String
)