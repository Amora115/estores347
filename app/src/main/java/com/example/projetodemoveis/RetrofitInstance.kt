package com.example.projetodemoveis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL_LOCATION = "https://places.googleapis.com/v1/places/"
    private const val BASE_URL_WEATHER = "https://api.weather.com/"

    fun getLocationApi(): LocationApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationApiService::class.java)
    }

    fun getWeatherApi(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}