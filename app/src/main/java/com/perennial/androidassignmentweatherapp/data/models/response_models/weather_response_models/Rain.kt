package com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: String = "",
)