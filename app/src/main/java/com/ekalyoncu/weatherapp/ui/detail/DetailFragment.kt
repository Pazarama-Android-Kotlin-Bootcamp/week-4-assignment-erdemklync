package com.ekalyoncu.weatherapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ekalyoncu.weatherapp.data.Daily
import com.ekalyoncu.weatherapp.databinding.FragmentDetailBinding
import com.ekalyoncu.weatherapp.util.setWeatherDegree

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.detailLiveData.observe(viewLifecycleOwner) { info ->
            val detailInfo = Daily.fromJson(info)
            setDailyInfo(detailInfo)
        }

        return binding.root
    }

    private fun setDailyInfo(daily: Daily) {
        with(binding) {
            with(daily) {
                cityName.text = ""
                dayDegreeValue.setWeatherDegree(daily.temp.day)
                eveningDegreeValue.setWeatherDegree(daily.temp.eve)
                nightDegreeValue.setWeatherDegree(daily.temp.night)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}