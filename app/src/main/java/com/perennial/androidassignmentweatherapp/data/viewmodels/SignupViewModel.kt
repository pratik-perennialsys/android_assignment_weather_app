package com.perennial.androidassignmentweatherapp.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.implementation.SignupRepositoryImpl
import com.perennial.androidassignmentweatherapp.utils.FormValidationUtils
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import androidx.lifecycle.viewModelScope
import com.perennial.androidassignmentweatherapp.utils.MIN_PASSWORD_LEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: SignupRepositoryImpl
) : ViewModel() {

    var username: String = ""
    var password: String = ""
    var userEmail: String = ""
    var userConfirmPassword: String = ""
    private val _signupValidationResult = MutableLiveData<String>()
    val signupValidationLiveData: LiveData<String> = _signupValidationResult

    private val _signupResult = MutableLiveData<String>()
    val signupLiveData: LiveData<String> = _signupResult

    val allUsers: MutableLiveData<List<UserModelEntity>> = MutableLiveData()

    fun validateSignupForm() {
        if (username.isEmpty()) {
            _signupValidationResult.value = LoginSignupConstants.EN_INVALID_USER_NAME.toString()
            return
        }
        if (userEmail.isEmpty() || !FormValidationUtils.validateEmail(userEmail)) {
            _signupValidationResult.value = LoginSignupConstants.EN_INVALID_EMAIL.toString()
            return
        }

        if (password.isEmpty()) {
            _signupValidationResult.value = LoginSignupConstants.EN_INVALID_PASSWORD.toString()
            return
        } else if (password.length < MIN_PASSWORD_LEN) {
            _signupValidationResult.value =
                LoginSignupConstants.EN_INVALID_PASSWORD_LENGTH.toString()
            return
        }

        if (userConfirmPassword.isEmpty()) {
            _signupValidationResult.value = LoginSignupConstants.EN_INVALID_PASSWORD.toString()
            return
        } else if (userConfirmPassword.length < MIN_PASSWORD_LEN) {
            _signupValidationResult.value =
                LoginSignupConstants.EN_INVALID_PASSWORD_LENGTH.toString()
            return
        } else if (userConfirmPassword != password) {
            _signupValidationResult.value = LoginSignupConstants.EN_PASSWORD_NOT_MATCHING.toString()
            return
        }
        _signupValidationResult.value = LoginSignupConstants.EN_FORM_VALIDATED.toString()
    }

    fun performSignup() {
        viewModelScope.launch {
            val signupStatus = repository.insertSignedUpUser(
                UserModelEntity(
                    userId = null,
                    userEmail = userEmail,
                    userName = username,
                    userPassword = password
                )
            )
            if (signupStatus == LoginSignupConstants.EN_SIGNUP_FAIL_REASON.toString().toLong()) {
                _signupResult.value = LoginSignupConstants.EN_USER_ALREADY_EXIST.toString()
            } else if (signupStatus > 0) {
                _signupResult.value = LoginSignupConstants.EN_SIGNUP_SUCCESS.toString()
            } else
                _signupResult.value = LoginSignupConstants.EN_SIGNUP_FAILED.toString()
        }
    }

}