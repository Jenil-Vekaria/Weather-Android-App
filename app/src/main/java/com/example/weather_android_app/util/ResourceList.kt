package com.example.weather_android_app.util

sealed class ResourceList<T>(val data: List<T>?, val message: String?){
    class Success<T>(data: List<T>): ResourceList<T>(data,null)
    class Error<T>(message: String): ResourceList<T>(null,message)
}