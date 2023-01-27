package com.perennial.androidassignmentweatherapp.activities.login_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.activities.signup_activity.SignupActivity
import com.perennial.androidassignmentweatherapp.databinding.ActivityLoginBinding
import com.perennial.androidassignmentweatherapp.utils.FormValidationUtils

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding!!.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding!!.btnLogin.setOnClickListener {

        }
    }

}