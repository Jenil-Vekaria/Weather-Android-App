package com.example.weather_android_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.weather_android_app.data.model.CurrentWeather
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.databinding.LocationItemBinding
import com.example.weather_android_app.databinding.SingleWeatherDayItemBinding
import com.example.weather_android_app.util.Constants
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.round

class LocationAdapter(val listener: OnItemClickListener): ListAdapter<Location, LocationAdapter.LocationViewHolder>(Diffcallback())
{
    interface OnItemClickListener {
        fun onLocationClick(location: Location)
    }

    class Diffcallback: DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location) = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Location, newItem: Location) = oldItem == newItem
    }

    inner class LocationViewHolder(private val binding: LocationItemBinding): RecyclerView.ViewHolder(binding.root){

        init{
            binding.root.setOnClickListener {
                val location = getItem(adapterPosition)
                listener.onLocationClick(location)
            }
        }

        fun bind(location: Location){
            binding.apply{
                locationName.text = location.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
    }
}