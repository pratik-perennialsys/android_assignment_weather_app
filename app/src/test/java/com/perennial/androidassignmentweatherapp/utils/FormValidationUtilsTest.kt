package com.perennial.androidassignmentweatherapp.utils

import junit.framework.TestCase
import org.junit.Test

class FormValidationUtilsTest {

    @Test
    fun validateEmail() {
        TestCase.assertTrue(
            !FormValidationUtils.validateEmail("")
        )

        TestCase.assertTrue(
            !FormValidationUtils.validateEmail("some random string")
        )

        TestCase.assertTrue(
            FormValidationUtils.validateEmail("test@testDomain.co")
        )

    }

}