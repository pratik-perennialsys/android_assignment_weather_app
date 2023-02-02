package com.perennial.androidassignmentweatherapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.data.models.entities.WeatherModelEntity
import com.perennial.androidassignmentweatherapp.databinding.LayoutSingleWeatherItemBinding
import com.perennial.androidassignmentweatherapp.utils.DateTimeConstants
import com.perennial.androidassignmentweatherapp.utils.DateTimeUtils
import com.perennial.androidassignmentweatherapp.utils.StringUtils


class WeatherListAdapter(
    private val weatherList: List<WeatherModelEntity>,
    private val context: Context
) : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    private lateinit var binding: LayoutSingleWeatherItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        binding = LayoutSingleWeatherItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentWeatherItem = weatherList[position]

        binding.tvCityCountry.text = StringUtils.concatStrings(
            currentWeatherItem.city!!,
            ", ",
            currentWeatherItem.country!!,
            ""
        )
        binding.tvSunrise.text = StringUtils.concatStrings(
            context.getString(
                R.string.txt_sunrise_time
            ),
            " ",
            DateTimeUtils.getUnixToLocalTime(currentWeatherItem.timeOfSunRise!!),
            " AM"
        )
        binding.tvSunset.text = StringUtils.concatStrings(
            context.getString(
                R.string.txt_sunset_time
            ),
            " ",
            DateTimeUtils.getUnixToLocalTime(currentWeatherItem.timeOfSunset!!),
            " AM"
        )

        binding.tvTemp.text = StringUtils.concatStrings(
            context.getString(
                R.string.txt_temp
            ), " ", DateTimeUtils.convertTemperatureFromKelvinToCelsius(
                currentWeatherItem.temperature!!
            ), context.getString(
                R.string.sym_degree
            )
        )

        binding.tvWeatherTime.text = StringUtils.concatStrings(
            context.getString(
                R.string.txt_time
            ), " ",
            currentWeatherItem.time_stamp!!, ""
        )

        currentWeatherItem.weatherCondition.let {
            if (it == DateTimeConstants.EN_RAIN.toString())
                binding.ivWeather.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_rain
                    )
                )
        }

        if (currentWeatherItem.isEvening == true)
            binding.ivWeather.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_night
                )
            )
    }

    override fun getItemCount(): Int = weatherList.size


    class WeatherViewHolder(
        binding: LayoutSingleWeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }
}
