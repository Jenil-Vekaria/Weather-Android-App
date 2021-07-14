package com.example.weather_android_app.ui.location

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weather_android_app.R

class LocationSearchFragment: Fragment(R.layout.fragment_location_search){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search,menu)

        val searchItem = menu.findItem(R.id.menu_location_search)
        searchItem.expandActionView()

        menu.removeItem(R.id.menu_celsius)
        menu.removeItem(R.id.menu_fahrenheit)
    }
}