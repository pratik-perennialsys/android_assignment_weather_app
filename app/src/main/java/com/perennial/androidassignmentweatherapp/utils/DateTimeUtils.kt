package com.perennial.androidassignmentweatherapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

object DateTimeUtils {
    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss a")
        return sdf.format(Date())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getUnixToLocalTime(unixTime: String): String {
        if (unixTime.isEmpty()) return ""

        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm:ss")
            val netDate = Date(unixTime.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun convertTemperatureFromKelvinToCelsius(temp: String): String {
        if (temp.isEmpty()) return ""
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        return "${df.format(temp.toDouble() - 273.15)}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isItEveningNow(): Boolean {
        val timeNow = LocalTime.now()
        val timeTo = LocalTime.parse(DateTimeConstants.EN_EVENING_TIME.toString())
        if (timeNow.isAfter(timeTo)) return true
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isItEveningTime(time: String): Boolean {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss a")

        try {
            val date1 = sdf.parse(time)
            val date2 = sdf.parse(DateTimeConstants.EN_EVENING_TIME.toString())
            return date1.before(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }
}