package com.perennial.androidassignmentweatherapp.utils

object StringUtils {

    fun concatStrings(
        str1: String,
        str2: String,
        str3: String,
        str4: String
    ): String {
        return java.lang.StringBuilder(str1 + str2 + str3 + str4).toString()
    }
}