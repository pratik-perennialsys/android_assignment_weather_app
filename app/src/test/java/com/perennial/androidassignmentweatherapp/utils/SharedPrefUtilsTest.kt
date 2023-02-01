package com.perennial.androidassignmentweatherapp.utils

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SharedPrefUtilsTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @Mock
    var sharedPrefs: SharedPreferences? = null

    @Mock
    var sharedPrefUtils: SharedPrefUtils? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        sharedPrefUtils = SharedPrefUtils(sharedPrefs!!)
    }

    @Test
    fun testLoginValidationEmptyEmail() {
        sharedPrefUtils?.isUserLoggedIn()

        sharedPrefUtils?.syncUserLoginToPreferences(
            UserModelEntity(
                userEmail = "test@test.co",
                userName = "testUser",
            )
        )
        TestCase.assertTrue(
            sharedPrefUtils?.getStringValue(SharedPrefConstants.EN_PREF_USER_EMAIL.toString()) ==
                    "test@test.co"
        )
    }
}