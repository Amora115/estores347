package com.example.projetodemoveis

import retrofit2.http.GET

interface LocationApiService {
    @GET("location")
    suspend fun getLocation(): LocationResponse
}

data class LocationResponse(
    val city: String,
    val country: String
)