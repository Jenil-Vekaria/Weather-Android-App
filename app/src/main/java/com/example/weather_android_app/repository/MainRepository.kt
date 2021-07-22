package com.example.weather_android_app.repository

import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.data.model.WeatherResponse
import com.example.weather_android_app.util.Resource
import com.example.weather_android_app.util.ResourceList

interface MainRepository {
    suspend fun getWeatherInfo(lat: String, lon: String, units: String): Resource<WeatherResponse>
    suspend fun getLocationList(name: String = "Tor"): ResourceList<Location>
}