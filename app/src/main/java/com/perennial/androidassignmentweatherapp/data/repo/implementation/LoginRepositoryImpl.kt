package com.perennial.androidassignmentweatherapp.data.repo.implementation

import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.LoginRepository
import com.perennial.androidassignmentweatherapp.data.room.dao.LoginDao
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val dao: LoginDao) : LoginRepository {

    override suspend fun performLogin(email: String, password: String): List<UserModelEntity> {
        return dao.performLogin(email, password)
    }
}