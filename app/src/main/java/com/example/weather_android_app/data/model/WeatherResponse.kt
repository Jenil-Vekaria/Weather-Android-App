package com.example.weather_android_app.data

import com.squareup.moshi.Json

data class WeatherResponse(
    val current: CurrentWeather,
    val hourly: List<CurrentWeather>,
    val daily: List<Temperature>
)

data class CurrentWeather(
    @Json(name="temp") val temperature: Temperature,
    @Json(name="wind_speed") val windSpeed: Double,
    @Json(name="weather") val weatherInfo: List<Weather>,
    val humidity: Int,
)

data class Weather(
    val description: String,
    val icon: String
)

data class Temperature(val temperature: Double)