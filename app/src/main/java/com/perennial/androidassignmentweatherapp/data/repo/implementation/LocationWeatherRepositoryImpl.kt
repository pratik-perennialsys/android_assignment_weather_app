package com.perennial.androidassignmentweatherapp.data.repo.implementation

import com.perennial.androidassignmentweatherapp.BuildConfig
import com.perennial.androidassignmentweatherapp.data.models.entities.WeatherModelEntity
import com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models.WeatherApiResponseModel
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.LocationWeatherRepository
import com.perennial.androidassignmentweatherapp.data.rest.WeatherApiService
import com.perennial.androidassignmentweatherapp.data.room.dao.LocationWeatherDao
import retrofit2.Response
import javax.inject.Inject

class LocationWeatherRepositoryImpl @Inject constructor(
    private val dao: LocationWeatherDao,
    private val apiService: WeatherApiService
) : LocationWeatherRepository {

    override suspend fun callWeatherApi(_lat: String, _lng: String): Response<WeatherApiResponseModel> {
        return apiService.getWeatherByLocation(_lat, _lng, BuildConfig.OPEN_WEATHER_API_KEY)
    }

    override suspend fun insertWeatherToRoom(weatherModelEntity: WeatherModelEntity): Long {
        return dao.insert(weatherModelEntity)
    }

    override suspend fun getPreviousWeatherRecords(): List<WeatherModelEntity> {
        return dao.getPreviousWeatherRecords()
    }

}