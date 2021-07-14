package com.example.weather_android_app.repository

import com.example.weather_android_app.data.WeatherApi
import com.example.weather_android_app.data.WeatherResponse
import com.example.weather_android_app.util.Resource
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val api: WeatherApi
): MainRepository {

    override suspend fun getWeatherInfo(): Resource<WeatherResponse> {
        return try {
            val response = api.getWeatherInformation()
            val result = response.body()

            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch (e: Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}