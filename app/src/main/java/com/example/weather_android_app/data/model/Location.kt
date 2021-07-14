package com.example.weather_android_app.data.model

import com.squareup.moshi.Json

data class Location(
    val title: String,
    @Json(name="latt_long") val coordinate: String
)