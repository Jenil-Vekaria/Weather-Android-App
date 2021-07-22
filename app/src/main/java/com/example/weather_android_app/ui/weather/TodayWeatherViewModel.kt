package com.example.weather_android_app.ui.weather

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.data.model.WeatherResponse
import com.example.weather_android_app.repository.MainRepository
import com.example.weather_android_app.util.Network
import com.example.weather_android_app.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TodayWeatherViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    @Assisted private val state: SavedStateHandle,
    private val network: Network
) : ViewModel() {

    sealed class WeatherEvent {
        class Success(val weatherResponse: WeatherResponse) : WeatherEvent()
        class Error(val message: String) : WeatherEvent()
        object Loading : WeatherEvent()
        object Empty : WeatherEvent()
    }

    var currentCity = "Toronto"
    var lat = "43.648560"
    var lon = "-79.385368"

    private var _weatherData = MutableStateFlow<WeatherEvent>(WeatherEvent.Empty)
    val weatherData: StateFlow<WeatherEvent> = _weatherData

    init {
        _weatherData.value = WeatherEvent.Empty
        getWeatherData()
    }

    fun getWeatherData(units: String = "metric") = viewModelScope.launch {
        _weatherData.value = WeatherEvent.Loading
        when (val response = repository.getWeatherInfo(lat,lon, units)) {
            is Resource.Error -> {
                if(!network.isNetworkAvailable())
                    _weatherData.value = WeatherEvent.Error("No Internet Connection")
                else
                    _weatherData.value = WeatherEvent.Error(response.message!!)
            }
            is Resource.Success -> {
                _weatherData.value = WeatherEvent.Success(response.data!!)
            }
        }
    }

}