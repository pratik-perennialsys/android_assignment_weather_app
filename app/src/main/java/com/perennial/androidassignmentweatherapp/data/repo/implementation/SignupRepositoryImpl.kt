package com.perennial.androidassignmentweatherapp.data.repo.implementation

import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.SignupRepository
import com.perennial.androidassignmentweatherapp.data.room.dao.SignupDao
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val dao: SignupDao): SignupRepository {

    override suspend fun insertSignedUpUser(user: UserModelEntity): Long{
        return dao.insert(user)
    }

}