package com.perennial.androidassignmentweatherapp.data.repo.interfaces

import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity

interface SignupRepository {
    suspend fun insertSignedUpUser(user: UserModelEntity): Long
}