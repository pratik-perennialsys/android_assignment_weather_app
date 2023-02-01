package com.perennial.androidassignmentweatherapp.utils

import junit.framework.TestCase
import org.junit.Test

class LocationUtilsTest {

    @Test
    fun getCurrentTime() {
        TestCase.assertTrue(
            LocationUtils.getLocationPermissionArray().size == 2
        )
    }
}