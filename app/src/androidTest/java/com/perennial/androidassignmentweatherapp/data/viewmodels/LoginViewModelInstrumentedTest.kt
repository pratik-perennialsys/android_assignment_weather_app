package com.perennial.androidassignmentweatherapp.data.viewmodels

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.perennial.androidassignmentweatherapp.data.models.await
import com.perennial.androidassignmentweatherapp.data.repo.implementation.LoginRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.repo.implementation.SignupRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.room.database.AppDatabase
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelInstrumentedTest : TestCase() {
    private lateinit var viewModel: LoginViewModel
    lateinit var sharedPrefUtils: SharedPrefUtils
    lateinit var sharedPrefs: SharedPreferences

    private lateinit var viewModel2: SignupViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        sharedPrefs = context.getSharedPreferences(
            SharedPrefConstants.EN_PREF_FILE_NAME.toString(),
            MODE_PRIVATE
        )
        sharedPrefUtils = SharedPrefUtils(sharedPrefs)

        val repo = LoginRepositoryImpl(db.loginDatabaseDao)

        viewModel = LoginViewModel(repo)
        val repo2 = SignupRepositoryImpl(db.registerDatabaseDao)
        viewModel2 = SignupViewModel(repo2)
    }

    @Test
    fun testLoginValidationEmptyEmail() {
        runBlocking {
            viewModel.userEmail = ""
            viewModel.validateLoginForm()
            val data = viewModel.loginValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_EMAIL.toString())
        }
    }

    @Test
    fun testLoginValidationInvalidEmail() {
        runBlocking {
            viewModel.userEmail = "SomeRandomString"
            viewModel.validateLoginForm()
            val data = viewModel.loginValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_EMAIL.toString())
        }
    }

    @Test
    fun testLoginValidationEmptyPassword() {
        runBlocking {
            viewModel.userEmail = "test@testDomain.co"
            viewModel.password = ""
            viewModel.validateLoginForm()
            val data = viewModel.loginValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_INVALID_PASSWORD.toString())
        }
    }

    @Test
    fun testLoginValidationValidCredentials() {
        runBlocking {
            viewModel.userEmail = "someEmail@someDomain.co"
            viewModel.password = "111111"
            viewModel.validateLoginForm()
            val data = viewModel.loginValidationLiveData.await()
            assertTrue(data == LoginSignupConstants.EN_FORM_VALIDATED.toString())
        }
    }

    @Test
    fun testUnregisteredUserLogin() {
        runBlocking {
            val email = "test@test.co"
            val password = "11111"
            viewModel.performLogin(email, password)

            val data = viewModel.loginResultLiveData.await()
            assertTrue(data.isEmpty())
        }
    }

    @Test
    fun testLogin() {
        runBlocking {
            viewModel2.username = "John"
            viewModel2.userEmail = "test@test.co"
            viewModel2.password = "11111"
            viewModel2.userConfirmPassword = "11111"
            viewModel2.performSignup()

            val email = "test@test.co"
            val password = "11111"
            viewModel.performLogin(email, password)

            val data = viewModel.loginResultLiveData.await()
            assertTrue(data.isNotEmpty() && data[0].userEmail == email)
            assertTrue(sharedPrefUtils.isUserLoggedIn())
        }
    }
}