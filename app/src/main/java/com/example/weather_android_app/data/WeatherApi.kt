package com.example.weather_android_app.data

import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    @GET("data/2.5/onecall?lat=43.648560&lon=-79.385368&exclude=minutely,alerts&units=metric&appid=d875a99a411c3c52d45d763ae33e6bd9")
    suspend fun getWeatherInformation(): Response<WeatherResponse>

}