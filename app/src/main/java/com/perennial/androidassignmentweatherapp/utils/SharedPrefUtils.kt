package com.perennial.androidassignmentweatherapp.utils

import android.content.SharedPreferences
import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity
import javax.inject.Inject

class SharedPrefUtils @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun syncUserLoginToPreferences (user: UserModelEntity) {
        sharedPreferences.edit().putBoolean(SharedPrefConstants.EN_PREF_USER_LOGGED_IN.toString(), true).apply()
        sharedPreferences.edit().putString(SharedPrefConstants.EN_PREF_USER_EMAIL.toString(), user.userEmail).apply()
        sharedPreferences.edit().putString(SharedPrefConstants.EN_PREF_USER_NAME.toString(), user.userName).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(SharedPrefConstants.EN_PREF_USER_LOGGED_IN.toString(), false)
    }

    fun clearSharedPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}
