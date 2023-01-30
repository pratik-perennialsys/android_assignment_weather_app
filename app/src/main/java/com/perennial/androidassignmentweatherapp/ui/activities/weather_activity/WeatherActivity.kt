package com.perennial.androidassignmentweatherapp.ui.activities.weather_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.databinding.ActivityWeatherBinding
import com.perennial.androidassignmentweatherapp.ui.activities.login_activity.LoginActivity
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    @Inject
    lateinit var sharedPrefUtils: SharedPrefUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        init()
    }

    private fun init() {
        binding.toolbar.tvToolbarTitle.text = getString(R.string.txt_title_signup)
        binding.toolbar.icToolbarStart.visibility = View.GONE
        binding.toolbar.tvToolbarTitle.text = getString(R.string.txt_weather_title)

        binding.toolbar.icToolbarEnd.visibility = View.VISIBLE
        binding.toolbar.icToolbarEnd.setOnClickListener {
            sharedPrefUtils.clearSharedPreferences()
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}