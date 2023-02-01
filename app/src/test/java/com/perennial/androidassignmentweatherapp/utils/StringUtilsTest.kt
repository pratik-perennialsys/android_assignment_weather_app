package com.perennial.androidassignmentweatherapp.utils

import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Test

class StringUtilsTest {
    @Test
    fun testConcat() {
        TestCase.assertTrue(StringUtils.concatStrings("John", "Doe", "@TestDomain",".com") ==
        "JohnDoe@TestDomain.com")
    }
}