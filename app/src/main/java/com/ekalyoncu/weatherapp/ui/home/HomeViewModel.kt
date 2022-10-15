package com.ekalyoncu.weatherapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekalyoncu.weatherapp.data.WeatherResponse
import com.ekalyoncu.weatherapp.service.ApiClient
import com.ekalyoncu.weatherapp.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {

    private val _homeLiveData = MutableLiveData<WeatherResponse>()
    val homeLiveData: LiveData<WeatherResponse> = _homeLiveData

    init {
        getWeatherInfo()
    }

    private fun getWeatherInfo() {
        ApiClient.getApiService().getWeatherInfo(
            lat = Constants.LATITUDE,
            lon = Constants.LONGITUDE,
            exclude = Constants.EXCLUDE,
            units = Constants.UNITS,
            lang = Constants.LANG,
        ).enqueue(
            object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        weatherResponse?.let {
                            _homeLiveData.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {}
            }
        )
    }
}