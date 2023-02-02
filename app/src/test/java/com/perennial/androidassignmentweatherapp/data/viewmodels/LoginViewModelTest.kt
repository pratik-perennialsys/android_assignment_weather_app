package com.perennial.androidassignmentweatherapp.data.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.implementation.LoginRepositoryImpl
import com.perennial.androidassignmentweatherapp.getOrAwaitValue
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @Mock
    var repo: LoginRepositoryImpl? = null

    var viewModel: LoginViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = LoginViewModel(repo!!)
    }

    @Test
    fun testLoginValidationEmptyEmail() {
        viewModel?.userEmail = ""
        viewModel?.validateLoginForm()

        val result = viewModel?.loginValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_EMAIL.toString())
    }

    @Test
    fun testLoginValidationInvalidEmail() {
        viewModel?.userEmail = "SomeRandomString"
        viewModel?.validateLoginForm()

        val result = viewModel?.loginValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_EMAIL.toString())
    }

    @Test
    fun testLoginValidationEmptyPassword() {
        viewModel?.userEmail = "pratik@test.co"
        viewModel?.password = ""
        viewModel?.validateLoginForm()

        val result = viewModel?.loginValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_PASSWORD.toString())
    }


    @Test
    fun testLoginValidationValidCredentials() {
        viewModel?.userEmail = "someEmail@someDomain.co"
        viewModel?.password = "111111"
        viewModel?.validateLoginForm()

        val result = viewModel?.loginValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_FORM_VALIDATED.toString())

    }

    @Test
    fun testLogin() =
        runBlocking {
            val email = "pratik@test.co"
            val password = "111111"
            val list = ArrayList<UserModelEntity>()
            list.add(UserModelEntity(userEmail = email, userPassword = password))
            Mockito.`when`(repo?.performLogin(email, password)).thenReturn(list)

            viewModel?.performLogin(email, password)
            dispatcher.scheduler.advanceUntilIdle()

            val result = viewModel?.loginResultLiveData?.getOrAwaitValue()
            TestCase.assertTrue(result != null && result.isNotEmpty() && result[0].userEmail == email)
        }


    @Test
    fun testUnregisteredUserLogin() =
        runBlocking {
            val email = "pratik@test.co"
            val password = "111111"
            Mockito.`when`(repo?.performLogin(email, password)).thenReturn(null)

            viewModel?.performLogin(email, password)
            dispatcher.scheduler.advanceUntilIdle()

            val result = viewModel?.loginResultLiveData?.getOrAwaitValue()
            TestCase.assertTrue(result == null)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}