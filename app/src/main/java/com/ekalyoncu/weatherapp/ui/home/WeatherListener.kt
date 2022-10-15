package com.ekalyoncu.weatherapp.ui.home

import android.view.View
import com.ekalyoncu.weatherapp.data.Daily

interface WeatherListener {
    fun onClick(daily: Daily)
}