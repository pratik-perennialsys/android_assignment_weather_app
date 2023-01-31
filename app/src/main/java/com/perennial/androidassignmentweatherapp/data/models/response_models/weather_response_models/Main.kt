package com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models

data class Main(
    val temp: String = "",
    val feels_like: String = "",
    val temp_min: String = "",
    val temp_max: String = "",
    val pressure: String = "",
    val humidity: String = "",
    val sea_level: String = "",
    val grnd_level: String = "",
)