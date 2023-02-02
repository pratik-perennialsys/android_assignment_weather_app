package com.perennial.androidassignmentweatherapp.data.viewmodels

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.perennial.androidassignmentweatherapp.data.models.await
import com.perennial.androidassignmentweatherapp.data.repo.implementation.SignupRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.room.database.AppDatabase
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignupViewModelInstrumentedTest : TestCase() {
    private lateinit var viewModel: SignupViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()


        val repo = SignupRepositoryImpl(db.registerDatabaseDao)
        viewModel = SignupViewModel(repo)
    }

    @Test
    fun testSignupValidationEmptyUserName() {
        runBlocking {
            viewModel.username = ""
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_USER_NAME.toString())
        }
    }

    @Test
    fun testSignupValidationEmptyEmail() {
        runBlocking {
            viewModel.username = "John"
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_EMAIL.toString())
        }
    }

    @Test
    fun testSignupValidationInvalidEmail() {
        runBlocking {
            viewModel.username = "John"
            viewModel.userEmail = "test"
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_EMAIL.toString())
        }
    }

    @Test
    fun testSignupValidationEmptyPassword() {
        runBlocking {
            viewModel.username = "John"
            viewModel.userEmail = "test@test.co"
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_PASSWORD.toString())
        }
    }

    @Test
    fun testSignupValidationInvalidPassword() {
        runBlocking {
            viewModel.username = "John"
            viewModel.userEmail = "test@test.co"
            viewModel.password = "1111"
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_PASSWORD_LENGTH.toString())
        }
    }

    @Test
    fun testSignupValidationDifferentConfirmPassword() {
        runBlocking {
            viewModel.username = "John"
            viewModel.userEmail = "test@test.co"
            viewModel.password = "11111"
            viewModel.userConfirmPassword = "111112"
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_PASSWORD_NOT_MATCHING.toString())
        }
    }

    @Test
    fun testSignupValidationWithValidFields() {
        runBlocking {
            viewModel.username = "John"
            viewModel.userEmail = "test@test.co"
            viewModel.password = "11111"
            viewModel.userConfirmPassword = "11111"
            viewModel.validateSignupForm()
            val data = viewModel.signupValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_FORM_VALIDATED.toString())
        }
    }

    @Test
    fun testSignup() {
        runBlocking {
            viewModel.username = "John"
            viewModel.userEmail = "test@test.co"
            viewModel.password = "11111"
            viewModel.userConfirmPassword = "11111"
            viewModel.performSignup()
            val data = viewModel.signupLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_SIGNUP_SUCCESS.toString())
        }
    }

}