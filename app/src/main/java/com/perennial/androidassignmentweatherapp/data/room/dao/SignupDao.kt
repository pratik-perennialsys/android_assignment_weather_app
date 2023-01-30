package com.perennial.androidassignmentweatherapp.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity

@Dao
interface SignupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(register: UserModelEntity) : Long
}