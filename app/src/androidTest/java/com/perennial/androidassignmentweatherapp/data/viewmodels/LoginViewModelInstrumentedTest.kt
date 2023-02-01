package com.perennial.androidassignmentweatherapp.data.viewmodels

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.perennial.androidassignmentweatherapp.data.repo.implementation.LoginRepositoryImpl
import com.perennial.androidassignmentweatherapp.data.room.database.AppDatabase
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelInstrumentedTest : TestCase() {
    private lateinit var viewModel: LoginViewModel
    lateinit var sharedPrefUtils: SharedPrefUtils
    lateinit var sharedPrefs: SharedPreferences

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
        val sharedPrefUtils = SharedPrefUtils(sharedPrefs)

        val repo = LoginRepositoryImpl(db.loginDatabaseDao)
        viewModel = LoginViewModel(repo, sharedPrefUtils)
    }

    @Test
    fun testLoginValidationEmptyEmail() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.userEmail = ""
            viewModel.validateLoginForm()
            viewModel.loginValidationLiveData.observeForever { data ->
                {
                    assertTrue(data == LoginSignupConstants.EN_INVALID_EMAIL.toString())
                }
            }
        }
    }

    @Test
    fun testLoginValidationInvalidEmail() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.userEmail = "SomeRandomString"
            viewModel.validateLoginForm()
            viewModel.loginValidationLiveData.observeForever { data ->
                assertTrue(data == LoginSignupConstants.EN_INVALID_EMAIL.toString())
            }
        }
    }

    @Test
    fun testLoginValidationEmptyPassword() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.password = ""
            viewModel.validateLoginForm()
            viewModel.loginValidationLiveData.observeForever { data ->
                assertTrue(data == LoginSignupConstants.EN_INVALID_PASSWORD.toString())
            }
        }
    }

    @Test
    fun testLoginValidationValidCredentials() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.userEmail = "someEmail@someDomain.co"
            viewModel.password = "111111"
            viewModel.validateLoginForm()
            viewModel.loginValidationLiveData.observeForever { data ->
                assertTrue(data == LoginSignupConstants.EN_FORM_VALIDATED.toString())
            }
        }
    }

    @Test
    fun testLogin() {
        GlobalScope.launch(Dispatchers.Main) {
            val email = "someEmail@someDomain.co"
            val password = "111111"
            viewModel.performLogin(email,password )
            viewModel.loginResultLiveData.observeForever { data ->
                assertTrue(data!=null && data.isNotEmpty() && data[0].userEmail == email)
                assertTrue(sharedPrefUtils.isUserLoggedIn())
            }
        }
    }
}