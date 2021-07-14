package com.example.weather_android_app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.weather_android_app.data.model.CurrentWeather
import com.example.weather_android_app.databinding.SingleWeatherDayItemBinding
import com.example.weather_android_app.util.Constants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.round

class WeekWeatherAdapter @Inject constructor(
    val glide: RequestManager
): ListAdapter<CurrentWeather, WeekWeatherAdapter.WeekWeatherViewHolder>(Diffcallback())
{
    class Diffcallback: DiffUtil.ItemCallback<CurrentWeather>() {
        override fun areItemsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather) = oldItem.datetime == newItem.datetime
        override fun areContentsTheSame(oldItem: CurrentWeather, newItem: CurrentWeather) = oldItem == newItem
    }

    inner class WeekWeatherViewHolder(private val binding: SingleWeatherDayItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(currentWeather: CurrentWeather){
            binding.apply{
                val dateFormatter = SimpleDateFormat("EE", Locale.getDefault())
                val date = Date(currentWeather.datetime * 1000)

                val formatted = dateFormatter.format(date).subSequence(0,3)
                val temperature = "${round(currentWeather.temperature.temperature).toInt().toString()}°"
                glide.load(Constants.OPEN_WEATHER_ICON+"${currentWeather.weatherInfo[0].icon}.png").into(weatherIcon)
                weatherDay.text = formatted
                weatherTemperature.text = temperature
                weatherDescription.text = currentWeather.weatherInfo[0].main
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val binding = SingleWeatherDayItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return WeekWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        val currentWeather = getItem(position)
        holder.bind(currentWeather)
    }
}