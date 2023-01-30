package com.perennial.androidassignmentweatherapp.data.repo.interfaces

import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity

interface LoginRepository {
    suspend fun performLogin(email: String, password: String): List<UserModelEntity>
}