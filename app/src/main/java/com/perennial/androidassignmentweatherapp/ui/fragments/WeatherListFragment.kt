package com.perennial.androidassignmentweatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.perennial.androidassignmentweatherapp.data.viewmodels.LocationWeatherViewModel
import com.perennial.androidassignmentweatherapp.databinding.FragmentWeatherListBinding
import com.perennial.androidassignmentweatherapp.ui.adapters.WeatherListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherListFragment : Fragment() {
    private lateinit var adapter: WeatherListAdapter
    private val viewModel: LocationWeatherViewModel by activityViewModels()
    private lateinit var binding: FragmentWeatherListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if(viewModel.weatherListLiveData.value!=null) {
            adapter = WeatherListAdapter(
                context = requireContext(),
                weatherList = viewModel.weatherListLiveData.value!!
            )
            binding.rvWeatherList.layoutManager = LinearLayoutManager(context)
            binding.rvWeatherList.adapter = adapter
        }
    }
}