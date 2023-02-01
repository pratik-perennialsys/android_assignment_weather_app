package com.perennial.androidassignmentweatherapp.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perennial.androidassignmentweatherapp.data.models.entities.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.repo.implementation.LoginRepositoryImpl
import com.perennial.androidassignmentweatherapp.utils.FormValidationUtils
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repo: LoginRepositoryImpl,
                                         private val sharedPrefUtils: SharedPrefUtils
) : ViewModel() {
    var password: String = ""
    var userEmail: String = ""

    private val _loginValidationResult = MutableLiveData<String>()
    val loginValidationLiveData: LiveData<String> = _loginValidationResult

    private val _loginResult = MutableLiveData<List<UserModelEntity>>()
    val loginResultLiveData: LiveData<List<UserModelEntity>> = _loginResult

    fun validateLoginForm() {
        if (userEmail.isEmpty() || !FormValidationUtils.validateEmail(userEmail)) {
            _loginValidationResult.postValue(LoginSignupConstants.EN_INVALID_EMAIL.toString())
            return
        }
        if (password.isEmpty()) {
            _loginValidationResult.postValue(LoginSignupConstants.EN_INVALID_PASSWORD.toString())
            return
        }
        _loginValidationResult.postValue(LoginSignupConstants.EN_FORM_VALIDATED.toString())
    }

    fun performLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loginResult = repo.performLogin(email, password)
            if(loginResult!=null && loginResult.isNotEmpty())
                sharedPrefUtils.syncUserLoginToPreferences(loginResult[0])
            _loginResult.postValue(loginResult)
        }
    }
}
