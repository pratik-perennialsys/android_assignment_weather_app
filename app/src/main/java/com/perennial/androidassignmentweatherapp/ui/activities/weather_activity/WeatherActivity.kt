package com.perennial.androidassignmentweatherapp.ui.activities.weather_activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.perennial.androidassignmentweatherapp.R
import com.perennial.androidassignmentweatherapp.data.viewmodels.LocationWeatherViewModel
import com.perennial.androidassignmentweatherapp.databinding.ActivityWeatherBinding
import com.perennial.androidassignmentweatherapp.ui.activities.login_activity.LoginActivity
import com.perennial.androidassignmentweatherapp.ui.fragments.CurrentWeatherFragment
import com.perennial.androidassignmentweatherapp.ui.fragments.WeatherListFragment
import com.perennial.androidassignmentweatherapp.utils.LocationUtils
import com.perennial.androidassignmentweatherapp.utils.SharedPrefUtils
import com.perennial.androidassignmentweatherapp.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding

    @Inject
    lateinit var sharedPrefUtils: SharedPrefUtils
    private val viewModel: LocationWeatherViewModel by viewModels()

    private var locationRequest: LocationRequest? = null
    private val LOCATION_SETTING_REQUEST_CODE = 1003

    private val locationPermissionResultReceiver = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            checkClientSettings()
        } else {
            showPermissionDeniedToast()
        }
    }

    private fun showPermissionDeniedToast() {
        ToastUtils.showShortToast(this, getString(R.string.err_location_perms_needed))
        binding.clProgressBar.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        init()
        getCurrentLocation()
    }

    private fun init() {
        binding.toolbar.tvToolbarTitle.text = getString(R.string.txt_title_signup)
        binding.toolbar.icToolbarStart.visibility = View.GONE
        binding.toolbar.tvToolbarTitle.text = getString(R.string.txt_weather_title)

        binding.tabPrevWeather.setOnClickListener {
            replaceFragment(WeatherListFragment())
        }

        binding.tabCurrWeather.setOnClickListener {
            replaceFragment(CurrentWeatherFragment())
        }

        binding.toolbar.icToolbarEnd.visibility = View.VISIBLE
        binding.toolbar.icToolbarEnd.setOnClickListener {
            sharedPrefUtils.clearSharedPreferences()
            navigateToLogin()
        }

        viewModel.currentLocationLiveData.observe(this) { weatherLocationModel ->
            weatherLocationModel.let {
                if (it._lat != null && it._lng != null)
                    viewModel.getWeatherByLocation()
            }
        }

        viewModel.insertWeatherLiveData.observe(this) { weatherLocationModel ->
            weatherLocationModel.let {
                if (it > 0) {
                    viewModel.getPreviousWeatherRecords()
                    replaceFragment(CurrentWeatherFragment())
                }
            }
        }

        viewModel.weatherApiErrorResponse.observe(this) { error ->
            error.let {
                ToastUtils.showShortToast(this, error)
            }
        }
    }

    private fun navigateToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun getCurrentLocation() {
        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission() {
        if (LocationUtils.checkLocationPermissions(this)) {
            checkClientSettings()
            return
        }
        val locationPerms: ArrayList<String> = LocationUtils.getLocationPermissionArray()
        locationPermissionResultReceiver.launch(locationPerms.toTypedArray())
    }


    private fun checkClientSettings() {
        locationRequest = LocationRequest.create()
            .setInterval(1000)
            .setFastestInterval(500)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        val request = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!).build()

        val client = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(request)
        task.addOnSuccessListener {
            viewModel.getCurrentLocation(locationRequest!!)
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    startIntentSenderForResult(
                        e.resolution.intentSender,
                        LOCATION_SETTING_REQUEST_CODE, null, 0, 0, 0, null
                    )
                } catch (sendIntentException: IntentSender.SendIntentException) {
                    sendIntentException.printStackTrace()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_SETTING_REQUEST_CODE ->
                when (resultCode) {
                    Activity.RESULT_OK -> viewModel.getCurrentLocation(locationRequest!!)
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        this,
                        getString(R.string.err_need_location_service),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        binding.clProgressBar.visibility = View.GONE
        supportFragmentManager
            .beginTransaction()
            .replace(binding.flFragmentContainer.id, fragment)
            .commit()
    }
}