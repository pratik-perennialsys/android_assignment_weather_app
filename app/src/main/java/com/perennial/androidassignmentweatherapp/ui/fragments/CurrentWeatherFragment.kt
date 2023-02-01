package com.perennial.androidassignmentweatherapp.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.data.viewmodels.LocationWeatherViewModel
import com.perennial.androidassignmentweatherapp.databinding.FragmentCurrentWeatherBinding
import com.perennial.androidassignmentweatherapp.utils.DateTimeConstants
import com.perennial.androidassignmentweatherapp.utils.DateTimeUtils
import com.perennial.androidassignmentweatherapp.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
    private val viewModel: LocationWeatherViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initViews() {
        if(viewModel.weatherModelLiveData.value==null) return
        binding.tvCity.text = viewModel.weatherModelLiveData.value?.city
        binding.tvCountry.text = viewModel.weatherModelLiveData.value?.country
        binding.tvTemperature.text =
            StringUtils.concatStrings(
                getString(
                    R.string.txt_curr_temp
                ), " ", DateTimeUtils.convertTemperatureFromKelvinToCelsius(
                    viewModel.weatherModelLiveData.value?.temperature!!
                ), getString(
                    R.string.sym_degree
                )
            )

        binding.tvTimeSunrise.text = StringUtils.concatStrings(
            getString(
                R.string.txt_sunrise_time
            ),
            " ",
            DateTimeUtils.getUnixToLocalTime(viewModel.weatherModelLiveData.value?.timeOfSunRise!!),
            " AM"
        )

        binding.tvTimeSunset.text = StringUtils.concatStrings(
            getString(
                R.string.txt_sunset_time
            ),
            " ",
            DateTimeUtils.getUnixToLocalTime(viewModel.weatherModelLiveData.value?.timeOfSunset!!),
            " PM"
        )

        if (DateTimeUtils.isItEveningNow()) {
            binding.ivCurrWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_night))
        } else if (viewModel.weatherModelLiveData.value?.weatherCondition == DateTimeConstants.EN_RAIN.toString())
            binding.ivCurrWeather.setImageDrawable(resources.getDrawable(R.drawable.ic_rain))
    }
}