package com.example.weather_android_app.di.moshiadapter

import com.example.weather_android_app.data.model.Temperature
import com.squareup.moshi.*

class WeatherJsonAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Temperature {
        val jsonValue = reader.readJsonValue()
        return when (jsonValue) {
            is Double -> Temperature(jsonValue)
            else -> {
                val temperatureResponse = jsonValue as Map<String, Double?>
                Temperature(temperatureResponse["day"]!!)
            }
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Temperature?) {
        TODO("not implemented")
    }
}