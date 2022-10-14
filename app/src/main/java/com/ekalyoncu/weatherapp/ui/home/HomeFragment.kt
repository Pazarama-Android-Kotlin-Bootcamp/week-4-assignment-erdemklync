package com.ekalyoncu.weatherapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekalyoncu.weatherapp.BuildConfig
import com.ekalyoncu.weatherapp.R
import com.ekalyoncu.weatherapp.data.WeatherResponse
import com.ekalyoncu.weatherapp.databinding.FragmentHomeBinding
import com.ekalyoncu.weatherapp.service.ApiClient
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
            "41.08633553249694",
            "31.12887459693166",
            "minutely,hourly"
        ).enqueue(
            object : Callback<WeatherResponse> {
                @SuppressLint("LongLogTag")
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    Log.d("HomeFragment:", response.body().toString())
                    Log.d("HomeFragment (headers):", response.headers().toString())
                    Log.d("HomeFragment (call):", call.toString())
                    Log.d("HomeFragment (response):", response.raw().toString())

                    if (response.isSuccessful) {
                        val everything = response.body()
                        everything?.let {
                            Log.d("HomeFragment (success):", it.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.d("HomeFragment (t)", t.message.toString())
                    Log.d("HomeFragment (t)", t.localizedMessage.toString())
                    Log.d("HomeFragment (t)", t.stackTraceToString())
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}