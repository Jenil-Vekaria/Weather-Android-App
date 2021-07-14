package com.example.weather_android_app.repository

import com.example.weather_android_app.data.api.LocationApi
import com.example.weather_android_app.data.api.WeatherApi
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.data.model.WeatherResponse
import com.example.weather_android_app.util.Resource
import com.example.weather_android_app.util.ResourceList
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val locationApi: LocationApi
): MainRepository {

    override suspend fun getWeatherInfo(lat: String, lon: String): Resource<WeatherResponse> {
        return try {
            val response = weatherApi.getWeatherInformation(lat,lon)
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

    override suspend fun getLocationList(name: String): ResourceList<Location> {
        return try {
            val response = locationApi.getLocation(name)
            val result = response.body()

            if(response.isSuccessful && result != null){
                return ResourceList.Success(result)
            }
            else{
                ResourceList.Error(response.message())
            }
        }catch (e: Exception){
            ResourceList.Error(e.message ?: "An error occurred")
        }
    }
}