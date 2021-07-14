package com.example.weather_android_app.repository

import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.data.model.WeatherResponse
import com.example.weather_android_app.util.Resource

interface MainRepository {
    suspend fun getWeatherInfo(lat: String = "43.648560", lon: String = "-79.385368"): Resource<WeatherResponse>
    suspend fun getLocationList(name: String = "Tor"): Resource<Location>
}