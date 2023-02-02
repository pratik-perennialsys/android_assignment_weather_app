package com.perennial.androidassignmentweatherapp.data.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.implementation.SignupRepositoryImpl
import com.perennial.androidassignmentweatherapp.getOrAwaitValue
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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

class SignupViewModelTest {
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @Mock
    lateinit var repo: SignupRepositoryImpl
    var viewModel: SignupViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = SignupViewModel(repo)
    }

    @Test
    fun testSignupValidationEmptyUsername() {
        viewModel?.username = ""
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_USER_NAME.toString())
    }

    @Test
    fun testSignupValidationEmptyEmail() {
        viewModel?.username = "John"
        viewModel?.userEmail = ""
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_EMAIL.toString())
    }

    @Test
    fun testSignupValidationInvalidEmail() {
        viewModel?.username = "John"
        viewModel?.userEmail = "some random string"
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_EMAIL.toString())
    }

    @Test
    fun tesSignupValidationEmptyPassword() {
        viewModel?.username = "John"
        viewModel?.userEmail = "test@testDomain.com"
        viewModel?.password = ""
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_PASSWORD.toString())
    }

    @Test
    fun testSignupValidationInvalidPassword() {
        viewModel?.username = "John"
        viewModel?.userEmail = "test@testDomain.com"
        viewModel?.password = "1111"
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_PASSWORD_LENGTH.toString())
    }

    @Test
    fun testSignupValidationInvalidConfirmPassword() {
        viewModel?.username = "John"
        viewModel?.userEmail = "test@testDomain.com"
        viewModel?.password = "1111"
        viewModel?.userConfirmPassword = "11122"
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_INVALID_PASSWORD_LENGTH.toString())
    }

    @Test
    fun testSignupValidationValidCredentials() {
        viewModel?.username = "John"
        viewModel?.userEmail = "someEmail@someDomain.co"
        viewModel?.password = "111111"
        viewModel?.userConfirmPassword = "111111"
        viewModel?.validateSignupForm()
        val result = viewModel?.signupValidationLiveData?.value
        TestCase.assertTrue(result == LoginSignupConstants.EN_FORM_VALIDATED.toString())
    }

    @Test
    fun testSignup() =
        runBlocking {
            val email = "pratik@test.colj"
            val password = "111111"
            val userName = "pratik"
            val isUserLoggedIn = true

            viewModel?.userEmail = email
            viewModel?.password = password
            viewModel?.username = userName
            viewModel?.userConfirmPassword = password

            Mockito.`when`(
                repo.insertSignedUpUser(
                    UserModelEntity(
                        userEmail = email,
                        userPassword = password,
                        userName = userName,
                        isLoggedIn = isUserLoggedIn
                    )
                )
            ).thenReturn(1L)

            viewModel?.performSignup()
            dispatcher.scheduler.advanceUntilIdle()

            val result = viewModel?.signupLiveData?.getOrAwaitValue()
            TestCase.assertTrue(result != null && result.isNotEmpty() && result == LoginSignupConstants.EN_SIGNUP_FAILED.toString())
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}