package com.perennial.androidassignmentweatherapp.utils

import junit.framework.TestCase
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class DateTimeUtilsTest {

    @Test
    fun getCurrentTime() {
        TestCase.assertTrue(
            DateTimeUtils.getCurrentDateTime() == (SimpleDateFormat("dd/M/yyyy hh:mm:ss a").format(
                Date()
            ))
        )
    }

    @Test
    fun getUnixTimestampToReadableTime() {
        TestCase.assertTrue(
            DateTimeUtils.getUnixToLocalTime("1661834187").equals( "08/30/2022 10:06:27")
        )
        TestCase.assertTrue(
            DateTimeUtils.getUnixToLocalTime("42qeer45") == ""
        )
    }

    @Test
    fun getTemperatureFromKelvinToCelsius() {
        TestCase.assertTrue(
            DateTimeUtils.convertTemperatureFromKelvinToCelsius("300") == "26.85"
        )

        TestCase.assertTrue(
            DateTimeUtils.convertTemperatureFromKelvinToCelsius("some string") == ""
        )
    }

    @Test
    fun getIfEveningNow() {
        TestCase.assertTrue(
            DateTimeUtils.isItEveningNow() == LocalTime.now().isAfter(LocalTime.parse(DateTimeConstants.EN_EVENING_TIME.toString()))
        )
        TestCase.assertFalse(
            DateTimeUtils.isItEveningNow() == LocalTime.now().isBefore(LocalTime.parse(DateTimeConstants.EN_EVENING_TIME.toString()))
        )
    }
}