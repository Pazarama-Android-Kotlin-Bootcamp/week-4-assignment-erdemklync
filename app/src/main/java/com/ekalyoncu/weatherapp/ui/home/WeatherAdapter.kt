package com.ekalyoncu.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekalyoncu.weatherapp.data.Daily
import com.ekalyoncu.weatherapp.databinding.ItemWeatherBinding
import com.ekalyoncu.weatherapp.util.setDate
import com.ekalyoncu.weatherapp.util.setDayName
import com.ekalyoncu.weatherapp.util.setWeatherDegree

class WeatherAdapter(private val response: List<Daily>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Daily) {
            with(binding) {
                dayName.setDayName(daily.dt)
                dayDate.setDate(daily.dt)
                itemNextDayDegree.setWeatherDegree(daily.temp.day)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val daily: Daily = response.drop(1)[position]
        holder.bind(daily)
    }

    override fun getItemCount(): Int = response.size
}