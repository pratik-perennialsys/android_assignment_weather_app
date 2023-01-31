package com.perennial.androidassignmentweatherapp.data.rest

import com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models.WeatherApiResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET(ApiConstants.WEATHER_API_ENDPOINT)
    suspend fun getWeatherByLocation(
        @Query("lat") _lat: String,
        @Query("lon") _lon: String,
        @Query("appid") _appId: String
    ): Response<WeatherApiResponseModel>
}