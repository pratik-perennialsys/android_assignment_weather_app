package com.perennial.androidassignmentweatherapp.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity

@Dao
interface LoginDao {
    @Query("SELECT * FROM tb_user WHERE user_email LIKE :email AND password_text LIKE:password")
    fun performLogin(email: String, password: String): List<UserModelEntity>
}