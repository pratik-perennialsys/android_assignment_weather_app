package com.perennial.androidassignmentweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.models.entities.WeatherModelEntity

@Dao
interface LocationWeatherDao {
    @Insert()
    suspend fun insert(weather: WeatherModelEntity) : Long

    @Query("SELECT * FROM tb_weather ORDER BY time_stamp DESC")
    suspend fun getPreviousWeatherRecords() : List<WeatherModelEntity>
}