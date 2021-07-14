package com.example.weather_android_app.data.api

import com.example.weather_android_app.data.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("api/location/search/?")
    suspend fun getLocation(@Query("query") query: String): Response<Location>

}