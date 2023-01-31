package com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models

data class WeatherApiResponseModel(
    val coord: Coord? = null,
    val weather: List<Weather>? = null,
    val base: String = "",
    val main: Main? = null,
    val visibility: Long = 0L,
    val wind: Wind? = null,
    val rain: Rain? = null,
    val clouds: Clouds? = null,
    val dt: String = "",
    val sys: Sys? = null,
    val timezone: String = "",
    val id: String = "",
    val name: String = "",
    val cod: String = "",
)
