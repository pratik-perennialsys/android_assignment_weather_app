package com.perennial.androidassignmentweatherapp.ui.activities.signup_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.data.models.UserModelEntity
import com.perennial.androidassignmentweatherapp.data.viewmodels.SignupViewModel
import com.perennial.androidassignmentweatherapp.ui.activities.login_activity.LoginActivity
import com.perennial.androidassignmentweatherapp.databinding.ActivitySignupBinding
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import com.perennial.androidassignmentweatherapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private var binding: ActivitySignupBinding? = null
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        init()
    }

    private fun init() {
        binding?.toolbar?.tvToolbarTitle?.text = getString(R.string.txt_title_signup)
        binding?.toolbar?.icToolbarStart?.setOnClickListener {
            navigateToLogin()
        }

        binding?.viewModel = viewModel

        binding?.tvLogin?.setOnClickListener {
            navigateToLogin()
        }
        binding?.btnSignup?.setOnClickListener {
            viewModel.validateSignupForm()
        }

        viewModel.signupValidationLiveData.observe(this) { data ->
            if (data == LoginSignupConstants.EN_FORM_VALIDATED.toString())
                viewModel.performSignup()
            else
                ToastUtils.showShortToast(this, data)
        }

        viewModel.signupLiveData.observe(this) { data ->
            ToastUtils.showShortToast(this, data)
            if (data == LoginSignupConstants.EN_SIGNUP_SUCCESS.toString())
                navigateToLogin()
        }

        viewModel.allUsers.observe(this) { data ->
            if(data!=null)
                ToastUtils.showShortToast(this, data[0].userEmail!!)
        }
    }

    private fun navigateToLogin() {
        finish()
    }
}