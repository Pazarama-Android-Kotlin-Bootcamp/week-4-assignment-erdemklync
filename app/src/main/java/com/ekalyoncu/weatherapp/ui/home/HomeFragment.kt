package com.ekalyoncu.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekalyoncu.weatherapp.R
import com.ekalyoncu.weatherapp.data.Daily
import com.ekalyoncu.weatherapp.data.WeatherResponse
import com.ekalyoncu.weatherapp.databinding.FragmentHomeBinding
import com.ekalyoncu.weatherapp.service.ApiClient
import com.ekalyoncu.weatherapp.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

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

        viewModel.homeLiveData.observe(viewLifecycleOwner) { info ->
            setWeatherInfo(info)
        }
    }

    private fun setWeatherInfo(weatherResponse: WeatherResponse) {
        with(binding) {
            with(weatherResponse) {
                cityName.text = timezone
                date.setDate(current.dt)
                weatherDegree.setWeatherDegree(current.temp)
                weatherImage.setWeatherImage(current.weather[0].icon)
                weatherDescription.setWeatherDescription(current.weather[0].description)

                homeNextDaysRecyclerView.apply {
                    adapter = WeatherAdapter(
                        response = weatherResponse.daily,
                        listener = object : WeatherListener {
                            override fun onClick(daily: Daily) {
                                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(daily.toJson())
                                findNavController().navigate(action)
                            }
                        }
                    )
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}