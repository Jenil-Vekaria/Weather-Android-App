package com.example.weather_android_app.ui.weather

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_android_app.data.model.WeatherResponse
import com.example.weather_android_app.repository.MainRepository
import com.example.weather_android_app.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodayWeatherViewModel @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class WeatherEvent {
        class Success(val weatherResponse: WeatherResponse, message: String?) : WeatherEvent()
        class Error(val weatherResponse: WeatherResponse?, message: String) : WeatherEvent()
        object Loading : WeatherEvent()
        object Empty : WeatherEvent()
    }

    private var _weatherData = MutableStateFlow<WeatherEvent>(WeatherEvent.Empty)
    val weatherData: StateFlow<WeatherEvent> = _weatherData

    init {
        _weatherData.value = WeatherEvent.Empty
        getWeatherData()
    }

    fun getWeatherData() = viewModelScope.launch {
        _weatherData.value = WeatherEvent.Loading

        when (val response = repository.getWeatherInfo()) {
            is Resource.Error -> {
                _weatherData.value = WeatherEvent.Error(null, response.message!!)
                Log.d("MainActivity", "Error Occurred: ${response.message}")
            }
            is Resource.Success -> {
                _weatherData.value = WeatherEvent.Success(response.data!!, null)
            }
        }
    }

}