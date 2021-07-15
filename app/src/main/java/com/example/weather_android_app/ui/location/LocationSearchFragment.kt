package com.example.weather_android_app.ui.location

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_android_app.R
import com.example.weather_android_app.adapter.LocationAdapter
import com.example.weather_android_app.data.model.Location
import com.example.weather_android_app.databinding.FragmentLocationSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LocationSearchFragment: Fragment(R.layout.fragment_location_search), LocationAdapter.OnItemClickListener{

    private val viewModel: LocationSearchViewModel by viewModels()
    private lateinit var binding: FragmentLocationSearchBinding
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var searchItem: MenuItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLocationSearchBinding.bind(view)
        locationAdapter = LocationAdapter(this)

        binding.apply{
            locationRecyclerview.apply{
                layoutManager = LinearLayoutManager(requireContext())
                adapter = locationAdapter
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.locationList.collect { event ->
                when(event){
                    is LocationSearchViewModel.LocationEvent.Empty -> binding.loading.visibility = View.GONE
                    is LocationSearchViewModel.LocationEvent.Error -> binding.loading.visibility = View.GONE
                    is LocationSearchViewModel.LocationEvent.Loading -> binding.loading.visibility = View.GONE
                    is LocationSearchViewModel.LocationEvent.Success -> {
                        binding.loading.visibility = View.GONE
                        locationAdapter.submitList(event.locations)
                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search,menu)

        searchItem = menu.findItem(R.id.menu_location_search)
        val searchView = searchItem.actionView as SearchView
        searchItem.expandActionView()

        menu.removeItem(R.id.menu_celsius)
        menu.removeItem(R.id.menu_fahrenheit)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    viewModel.getLocationList(newText)
                }
                return true
            }

        })

    }

    override fun onLocationClick(location: Location) {
        searchItem.collapseActionView()
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set("location", location)
        navController.popBackStack()
    }
}