package com.example.weather_android_app.data.model

import com.squareup.moshi.Json

data class WeatherResponse(
    val timezone: String,
    val current: CurrentWeather,
    val daily: List<CurrentWeather>
)

data class CurrentWeather(
    @Json(name="temp") val temperature: Temperature,
    @Json(name="wind_speed") val windSpeed: Double,
    @Json(name="weather") val weatherInfo: List<Weather>,
    @Json(name="dt") val datetime: Long,
    val humidity: Int,
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Temperature(val temperature: Double)