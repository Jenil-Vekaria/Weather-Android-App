package com.example.weather_android_app.data.api

import com.example.weather_android_app.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?exclude=minutely,hourly,alerts&units=metric&appid=d875a99a411c3c52d45d763ae33e6bd9")
    suspend fun getWeatherInformation(
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): Response<WeatherResponse>

}