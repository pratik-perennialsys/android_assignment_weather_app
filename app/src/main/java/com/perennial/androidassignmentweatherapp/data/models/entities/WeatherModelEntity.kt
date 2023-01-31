package com.perennial.androidassignmentweatherapp.data.models.entities

import androidx.room.*

@Entity(
    tableName = "tb_weather"
)
data class WeatherModelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val userId: Int? = null,

    @ColumnInfo(name = "city")
    var city: String? = null,

    @ColumnInfo(name = "country")
    var country: String? = null,

    @ColumnInfo(name = "_lat")
    var _lat: Double? = null,

    @ColumnInfo(name = "_lng")
    var _lng: Double? = null,

    @ColumnInfo(name = "temperature")
    var temperature: String? = null,

    @ColumnInfo(name = "time_of_sunrise")
    var timeOfSunRise: String? = null,

    @ColumnInfo(name = "time_of_sunset")
    var timeOfSunset: String? = null,

    @ColumnInfo(name = "weather_condition")
    var weatherCondition: String? = null,

    @ColumnInfo(name = "time_stamp")
    var time_stamp: String? = null,
)
