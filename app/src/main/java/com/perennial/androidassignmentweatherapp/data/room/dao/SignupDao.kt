package com.perennial.androidassignmentweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity

@Dao
interface SignupDao {
    @Insert()
    suspend fun insert(register: UserModelEntity) : Long
}