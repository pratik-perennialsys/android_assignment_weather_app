package com.perennial.androidassignmentweatherapp.data.repo.implementation

import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.SignupRepository
import com.perennial.androidassignmentweatherapp.data.room.dao.SignupDao
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val dao: SignupDao): SignupRepository {

    override suspend fun insertSignedUpUser(user: UserModelEntity): Long {
        var insertStatus = -1L
        try{
            insertStatus = dao.insert(user)
        } catch(e: Exception) {
            if(e is net.sqlcipher.database.SQLiteConstraintException)
                insertStatus = LoginSignupConstants.EN_SIGNUP_FAIL_REASON.toString().toLong()
        }
        return insertStatus
    }

}