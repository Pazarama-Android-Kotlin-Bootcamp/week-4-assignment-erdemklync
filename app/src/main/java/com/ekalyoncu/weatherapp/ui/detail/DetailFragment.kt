package com.ekalyoncu.weatherapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ekalyoncu.weatherapp.R
import com.ekalyoncu.weatherapp.data.Daily
import com.ekalyoncu.weatherapp.databinding.FragmentDetailBinding
import com.ekalyoncu.weatherapp.databinding.FragmentHomeBinding

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
            val daily = Daily.fromJson(info)
            setDailyInfo(daily)
        }

        return binding.root
    }

    private fun setDailyInfo(daily: Daily) {
        with(binding) {
            with(daily) {
                //cityName.text = daily.
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}