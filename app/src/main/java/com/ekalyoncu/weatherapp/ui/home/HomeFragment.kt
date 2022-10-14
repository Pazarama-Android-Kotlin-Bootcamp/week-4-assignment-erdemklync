package com.ekalyoncu.weatherapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekalyoncu.weatherapp.R
import com.ekalyoncu.weatherapp.data.WeatherResponse
import com.ekalyoncu.weatherapp.databinding.FragmentHomeBinding
import com.ekalyoncu.weatherapp.service.ApiClient
import com.ekalyoncu.weatherapp.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEverything()
    }

    private fun getEverything() {
        ApiClient.getApiService().getWeatherInfo(
            lat = Constants.LATITUDE,
            lon = Constants.LONGITUDE,
            exclude = Constants.EXCLUDE,
            units = Constants.UNITS,
            lang = Constants.LANG,
        ).enqueue(
            object : Callback<WeatherResponse> {
                @SuppressLint("LongLogTag")
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                    if (response.isSuccessful) {
                        val weatherResponse = response.body()
                        weatherResponse?.let {
                            setCurrent(it)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {}
            }
        )
    }

    private fun setCurrent(weatherResponse: WeatherResponse) {
        with(binding) {

            with(weatherResponse) {
                cityName.text = timezone
                todayDate.setDate(current.dt)
                weatherDegree.setWeatherText(current.temp)
                weatherImage.setWeatherImage(current.weather[0].icon)
                weatherDescription.setWeatherDescription(current.weather[0].description)
            }


        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}