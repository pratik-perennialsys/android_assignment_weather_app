package com.perennial.androidassignmentweatherapp.data.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.perennial.androidassignmentweatherapp.data.models.entities.WeatherModelEntity
import com.perennial.androidassignmentweatherapp.data.models.response_models.weather_response_models.WeatherApiResponseModel
import com.perennial.androidassignmentweatherapp.data.repo.interfaces.LocationWeatherRepository
import com.perennial.androidassignmentweatherapp.utils.DateTimeUtils
import com.perennial.androidassignmentweatherapp.utils.LoginSignupConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class LocationWeatherViewModel @Inject constructor(
    private val repository: LocationWeatherRepository,
    private val appContext: Application,
) : ViewModel() {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private val _weatherModelLiveData = MutableLiveData<WeatherModelEntity>()
    val weatherModelLiveData = _weatherModelLiveData
    val insertWeatherLiveData: MutableLiveData<Long> = MutableLiveData()

    private val _weatherListLiveData = MutableLiveData<List<WeatherModelEntity>>()
    val weatherListLiveData: LiveData<List<WeatherModelEntity>> = _weatherListLiveData

    private val _currentLocationLiveData = MutableLiveData<WeatherModelEntity>()
    val currentLocationLiveData: LiveData<WeatherModelEntity> = _currentLocationLiveData

    private val _weatherApiErrorResponse = MutableLiveData<String>()
    val weatherApiErrorResponse: LiveData<String> = _weatherApiErrorResponse

    fun getCurrentLocation(locationRequest: LocationRequest) {
        viewModelScope.launch(Dispatchers.Default) {
            requestLocationUpdatesNow(locationRequest)
        }
    }

    fun getWeatherByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.callWeatherApi(
                _currentLocationLiveData.value?._lat!!.toString(),
                _currentLocationLiveData.value?._lng!!.toString()
            )
            res.let {
                if (res.isSuccessful) {
                    _weatherModelLiveData.postValue(getResponseToEntityObject(res.body()))
                    insertWeatherToRoom()
                } else {
                    var error = LoginSignupConstants.EN_SIGNUP_FAILED.toString()
                    try {
                        val jObjError = JSONObject(res.errorBody()?.string()!!)
                        error = jObjError.getString("message")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    _weatherApiErrorResponse.postValue(error)
                }
            }
        }
    }

    private fun insertWeatherToRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val insertStatus = repository.insertWeatherToRoom(_weatherModelLiveData.value!!)
            insertWeatherLiveData.postValue(insertStatus)
        }
    }

    fun getPreviousWeatherRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            val weatherRecordsList = repository.getPreviousWeatherRecords()

            _weatherListLiveData.postValue(weatherRecordsList)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdatesNow(locationRequest: LocationRequest) {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(appContext.applicationContext)
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    if (locationResult.locations.size == 0) return
                    for (location in locationResult.locations) {
                        location.let {
                            fusedLocationProviderClient?.removeLocationUpdates(this)
                            getPhysicalAddressFromCoordinates(
                                location.latitude,
                                location.longitude
                            )
                            return
                        }
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun getPhysicalAddressFromCoordinates(
        latitude: Double,
        longitude: Double
    ) {
        val geocoder = Geocoder(appContext.applicationContext, Locale.getDefault())
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val addresses: List<Address>? = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    5
                )
                if (addresses != null && addresses.isNotEmpty()) {
                    val city: String = addresses[0].locality ?: ""
                    val country: String? = addresses[0].countryName
                    _currentLocationLiveData.postValue(
                        WeatherModelEntity(
                            city = city,
                            country = country,
                            _lat = latitude,
                            _lng = longitude
                        )
                    )
                    return@launch
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getResponseToEntityObject(responseModel: WeatherApiResponseModel?): WeatherModelEntity? {
        return WeatherModelEntity(
            city = _currentLocationLiveData.value?.city,
            country = _currentLocationLiveData.value?.country,
            _lat = _currentLocationLiveData.value?._lat,
            _lng = _currentLocationLiveData.value?._lng,
            temperature = responseModel?.main?.temp,
            timeOfSunRise = responseModel?.sys?.sunrise,
            timeOfSunset = responseModel?.sys?.sunset,
            weatherCondition = responseModel?.weather?.get(0)?.main,
            time_stamp = DateTimeUtils.getCurrentDateTime(),
            isEvening = DateTimeUtils.isItEveningNow()
        )
    }
}