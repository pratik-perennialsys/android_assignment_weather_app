package com.perennial.androidassignmentweatherapp.utils

class Constants {

}

enum class RoomConstants(private val constName: String) {
    EN_DB_NAME("weather_app_db");
    override fun toString(): String {
        return constName
    }
}

enum class SharedPrefConstants(private val constName: String) {
    EN_PREF_FILE_NAME("pref_weather_app"),
    EN_PREF_USER_EMAIL("pref_user_email"),
    EN_PREF_USER_NAME("pref_user_name"),
    EN_PREF_USER_LOGGED_IN("pref_user_logged_in"),
    EN_PREF_USER_TOKEN("pref_user_access_token"),
    ;
    override fun toString(): String {
        return constName
    }
}

enum class LoginSignupConstants(val eventName: String) {
    EN_INVALID_USER_NAME("Username cannot be blank.."),
    EN_INVALID_PASSWORD("Password cannot be blank.."),
    EN_INVALID_PASSWORD_LENGTH("Password must be more than 5 characters.."),
    EN_INVALID_EMAIL(
        "Please enter valid email id.."
    ),
    EN_PASSWORD_NOT_MATCHING(
        "Password and confirm password not matching.."
    ),
    EN_FORM_VALIDATED("validated"),
    EN_SIGNUP_SUCCESS("Signed up successfully"),
    EN_SIGNUP_FAILED("Something went wrong, Please try again!"),
    EN_SIGNUP_FAIL_REASON("-999"),
    EN_USER_ALREADY_EXIST("This email is already registered!");

    override fun toString(): String {
        return eventName
    }
}

enum class DateTimeConstants(val eventName: String) {
    EN_RAIN("Rain"),
    EN_EVENING_TIME("18:00:00"),;

    override fun toString(): String {
        return eventName
    }
}

