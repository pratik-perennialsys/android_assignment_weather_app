package com.perennial.androidassignmentweatherapp.activities.signup_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.activities.login_activity.LoginActivity
import com.perennial.androidassignmentweatherapp.databinding.ActivitySignupBinding
import com.perennial.androidassignmentweatherapp.utils.FormValidationUtils
import com.perennial.androidassignmentweatherapp.utils.ToastUtils

class SignupActivity : AppCompatActivity() {
    private var binding: ActivitySignupBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        binding!!.tvLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
        binding!!.btnSignup.setOnClickListener {

        }
    }

}