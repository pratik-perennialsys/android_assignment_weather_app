package com.perennial.androidassignmentweatherapp.ui.activities.login_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.data.viewmodels.LoginViewModel
import com.perennial.androidassignmentweatherapp.databinding.ActivityLoginBinding
import com.perennial.androidassignmentweatherapp.ui.activities.signup_activity.SignupActivity
import com.perennial.androidassignmentweatherapp.ui.activities.weather_activity.WeatherActivity
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import com.perennial.androidassignmentweatherapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefUtils: SharedPrefUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()
    }

    private fun init() {
        if (sharedPrefUtils.isUserLoggedIn())
            navigateToWeatherActivity()
        binding?.viewModel = viewModel

        binding!!.btnLogin.setOnClickListener {
            viewModel.validateLoginForm()
        }
        binding!!.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        viewModel.loginValidationLiveData.observe(this) { data ->
            if (data == LoginSignupConstants.EN_FORM_VALIDATED.toString())
                viewModel.performLogin(
                    binding?.etEmail?.editText?.text.toString(),
                    binding?.etPassword?.editText?.text.toString()
                )
            else
                ToastUtils.showShortToast(this, data)
        }

        viewModel.loginResultLiveData.observe(this) { data ->
            if (data.isEmpty())
                ToastUtils.showShortToast(this, getString(R.string.err_login_failed))
            else {
                sharedPrefUtils.syncUserLoginToPreferences(data[0])
                ToastUtils.showShortToast(this, getString(R.string.login_success))
                navigateToWeatherActivity()
            }
        }
    }

    private fun navigateToWeatherActivity() {
        val i = Intent(this, WeatherActivity::class.java)
        startActivity(i)
        finish()
    }

}