package com.perennial.androidassignmentweatherapp.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.models.entities.WeatherModelEntity
import com.perennial.androidassignmentweatherapp.data.room.dao.LocationWeatherDao
import com.perennial.androidassignmentweatherapp.data.room.dao.LoginDao
import com.perennial.androidassignmentweatherapp.data.room.dao.SignupDao
import com.perennial.androidassignmentweatherapp.utils.RoomConstants

@Database(entities = [UserModelEntity::class, WeatherModelEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val registerDatabaseDao: SignupDao
    abstract val loginDatabaseDao: LoginDao
    abstract val locationWeatherDao: LocationWeatherDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        RoomConstants.EN_DB_NAME.toString()
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
