package com.perennial.androidassignmentweatherapp.data.repo.interfaces

import androidx.lifecycle.LiveData
import com.perennial.androidassignmentweatherapp.data.models.entities.WeatherModelEntity
import com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models.WeatherApiResponseModel
import retrofit2.Response

interface LocationWeatherRepository {
    suspend fun callWeatherApi(_lat: String, _lng: String) : Response<WeatherApiResponseModel>
    suspend fun insertWeatherToRoom(weatherModelEntity: WeatherModelEntity) : Long
    suspend fun getPreviousWeatherRecords() : List<WeatherModelEntity>
}