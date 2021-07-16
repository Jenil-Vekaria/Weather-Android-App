package com.example.weather_android_app.ui.location

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.repository.MainRepository
import com.example.weather_android_app.util.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class LocationSearchViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val network: Network
) : ViewModel() {

    sealed class LocationEvent() {
        class Success(val locations: List<Location>): LocationEvent()
        class Error(val message: String): LocationEvent()
        object Loading: LocationEvent()
        object Empty: LocationEvent()
    }

    private val _locationList = MutableStateFlow<LocationEvent>(LocationEvent.Empty)
    val locationList: StateFlow<LocationEvent> = _locationList

    init{
        _locationList.value = LocationEvent.Empty
    }

    fun getLocationList(name: String) = viewModelScope.launch {

        if(!network.isNetworkAvailable()){
            _locationList.value = LocationEvent.Error("No Internet Connection")
        }
        else{
            _locationList.value = LocationEvent.Loading

            val result = repository.getLocationList(name)
            _locationList.value = LocationEvent.Success(result.data!!)
        }
    }
}