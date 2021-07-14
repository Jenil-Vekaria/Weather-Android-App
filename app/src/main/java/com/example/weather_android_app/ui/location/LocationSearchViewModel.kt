package com.example.weather_android_app.ui.location

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LocationSearchViewModel @ViewModelInject constructor(
    private val repository: MainRepository
) : ViewModel() {

    sealed class LocationEvent() {
        class Success(locations: List<Location>)
    }

    val searchQuery = MutableStateFlow("")

    init{
        viewModelScope.launch {
            val result = repository.getLocationList()
            Log.d("MainActivity", "Locations: ${result.data}")
        }
    }

}