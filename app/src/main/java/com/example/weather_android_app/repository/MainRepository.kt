package com.example.weather_android_app.repository

import com.example.weather_android_app.data.model.WeatherResponse
import com.example.weather_android_app.util.Resource

interface MainRepository {
    suspend fun getWeatherInfo(): Resource<WeatherResponse>
}