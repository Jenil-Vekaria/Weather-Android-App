package com.example.weather_android_app.ui.weather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.weather_android_app.R
import com.example.weather_android_app.databinding.FragmentTodayWeatherBinding
import com.example.weather_android_app.ui.MainActivity
import com.example.weather_android_app.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_today_weather.*
import kotlinx.coroutines.flow.collect
import kotlin.math.round

@AndroidEntryPoint
class TodayWeatherFragment: Fragment(R.layout.fragment_today_weather) {

    private val viewModel: TodayWeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTodayWeatherBinding.bind(view)

        lifecycleScope.launchWhenStarted {
            viewModel.weatherData.collect { event ->
              when(event){
                  is TodayWeatherViewModel.WeatherEvent.Empty -> {}
                  is TodayWeatherViewModel.WeatherEvent.Error -> {

                  }
                  is TodayWeatherViewModel.WeatherEvent.Loading -> {

                  }
                  is TodayWeatherViewModel.WeatherEvent.Success -> {
                      val weatherData = event.weatherResponse

                      val weatherIcon = weatherData.current.weatherInfo[0].icon
                      val weatherDescription = weatherData.current.weatherInfo[0].description
                      val weatherTemperature = round(weatherData.current.temperature.temperature).toInt().toString()
                      val weatherWindSpeed = "${round(weatherData.current.windSpeed).toInt()} km/h"
                      val weatherHumidity = "${weatherData.current.humidity}%"

                      val mainActivity = activity as AppCompatActivity
                      mainActivity.supportActionBar?.title = "Toronto, Canada"

                      binding.apply {
                          Glide.with(root)
                              .load(Constants.OPEN_WEATHER_ICON+"$weatherIcon@2x.png")
                              .centerCrop()
                              .into(weather_icon)

                          weather_description.text = weatherDescription
                          weather_humidity.text = weatherHumidity
                          weather_temperature.text = weatherTemperature
                          weather_wind_speed.text = weatherWindSpeed
                      }
                  }
              }
            }
        }
        binding.apply{
            val weatherData =
            Glide.with(binding.root)
                .load(Constants.OPEN_WEATHER_ICON+"")
        }
    }
}