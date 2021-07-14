package com.example.weather_android_app.di.moshiadapter

import com.example.weather_android_app.data.Temperature
import com.squareup.moshi.*

class WeatherJsonAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Temperature {
        val jsonValue = reader.readJsonValue()
        return when (jsonValue) {
            is Double -> Temperature(jsonValue)
            else -> {
                val weatherResponse = jsonValue as Map<String, Any?>
                val temperature = weatherResponse["temp"] as Map<String, Double?>
                Temperature(temperature["day"]!!)
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Temperature?) {
        TODO("not implemented")
    }
}