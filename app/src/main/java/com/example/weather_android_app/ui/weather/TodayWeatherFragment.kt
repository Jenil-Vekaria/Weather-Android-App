package com.example.weather_android_app.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.weather_android_app.R
import com.example.weather_android_app.adapter.WeekWeatherAdapter
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.databinding.FragmentTodayWeatherBinding
import com.example.weather_android_app.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_today_weather.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.math.round

@AndroidEntryPoint
class TodayWeatherFragment : Fragment(R.layout.fragment_today_weather) {

    private val viewModel: TodayWeatherViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var weatherAdapter: WeekWeatherAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTodayWeatherBinding.bind(view)

        binding.apply {
            currentDayWeatherRecyclerview.apply {
                adapter = weatherAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }

        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Location>("location")
            ?.observe(viewLifecycleOwner) { location ->
                val coordinateList = location.coordinate.split(",")
                val lat = coordinateList[0]
                val lon = coordinateList[1]

                viewModel.currentCity = location.title
                viewModel.getWeatherData(lat,lon)
            }


        lifecycleScope.launchWhenStarted {
            viewModel.weatherData.collect { event ->
                when (event) {
                    is TodayWeatherViewModel.WeatherEvent.Empty -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    is TodayWeatherViewModel.WeatherEvent.Error -> {

                    }
                    is TodayWeatherViewModel.WeatherEvent.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    is TodayWeatherViewModel.WeatherEvent.Success -> {
                        binding.loading.visibility = View.GONE

                        val weatherData = event.weatherResponse

                        val weatherIcon = weatherData.current.weatherInfo[0].icon
                        val weatherDescription = weatherData.current.weatherInfo[0].description
                        val weatherTemperature =
                            round(weatherData.current.temperature.temperature).toInt().toString()
                        val weatherWindSpeed =
                            "${round(weatherData.current.windSpeed).toInt()} km/h"
                        val weatherHumidity = "${weatherData.current.humidity}%"

                        val mainActivity = activity as AppCompatActivity
                        mainActivity.supportActionBar?.title = viewModel.currentCity

                        binding.apply {
                            glide.load(Constants.OPEN_WEATHER_ICON + "$weatherIcon@2x.png")
                                .into(weather_icon)

                            weather_description.text = weatherDescription
                            weather_humidity.text = weatherHumidity
                            weather_temperature.text = weatherTemperature
                            weather_wind_speed.text = weatherWindSpeed
                        }

                        //Remove the first item - Current Day Weather
                        val sevenDayForcast = weatherData.daily.subList(1, 8)
                        weatherAdapter.submitList(sevenDayForcast)

                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_location_search -> {
                val action =
                    TodayWeatherFragmentDirections.actionTodayWeatherFragmentToLocationSearchFragment()
                findNavController().navigate(action)
                return true
            }
            R.id.menu_celsius -> {

                return true

            }
            R.id.menu_fahrenheit -> {

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}