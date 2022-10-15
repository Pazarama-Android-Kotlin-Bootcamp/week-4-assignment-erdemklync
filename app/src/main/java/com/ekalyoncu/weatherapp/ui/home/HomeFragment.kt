package com.ekalyoncu.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekalyoncu.weatherapp.R
import com.ekalyoncu.weatherapp.data.Daily
import com.ekalyoncu.weatherapp.data.DetailInfo
import com.ekalyoncu.weatherapp.data.WeatherResponse
import com.ekalyoncu.weatherapp.databinding.FragmentHomeBinding
import com.ekalyoncu.weatherapp.util.*

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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            setWeatherInfo(state.weatherResponse)

            binding.contentMain.isVisible = state.homeUiState is HomeUiState.Loaded
            binding.imgLoading.isVisible = state.homeUiState is HomeUiState.Loading
            binding.textLoading.isVisible = state.homeUiState is HomeUiState.Loading
        }
        return binding.root
    }

    private fun setWeatherInfo(weatherResponse: WeatherResponse) {
        with(binding) {
            with(weatherResponse) {
                cityName.text = timezone
                date.setDate(current.dt)
                weatherDegree.setWeatherDegree(current.temp)
                weatherImage.setWeatherImage(current.weather[0].icon)
                weatherDescription.setWeatherDescription(current.weather[0].description)

                homeSeeDetails.setOnClickListener {
                    val detailInfo = DetailInfo(timezone, weatherResponse.daily[0])
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(detailInfo.toJson())
                    findNavController().navigate(action)
                }

                homeNextDaysRecyclerView.apply {
                    adapter = WeatherAdapter(
                        response = weatherResponse.daily,
                        listener = object : WeatherListener {
                            override fun onClick(daily: Daily) {
                                val detailInfo = DetailInfo(timezone, daily)

                                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(detailInfo.toJson())
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