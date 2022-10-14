package com.ekalyoncu.weatherapp.util

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ekalyoncu.weatherapp.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun TextView.setWeatherText(degree: Double) {
    val formattedDegree = "%.1f".format(degree) + "°C"
    this.text = formattedDegree
}

fun ImageView.setWeatherImage(
    weatherCode: String,
) {
    val weatherImageUrl = "http://openweathermap.org/img/wn/${weatherCode}@4x.png"
    Glide
        .with(this)
        .load(weatherImageUrl)
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}

fun TextView.setWeatherDescription(
    description: String,
) {
    this.text = description
}

fun TextView.setDate(epochTime: Int) {
    val timeStamp = Timestamp(epochTime.toLong())
    val date = Date(timeStamp.time * 1000)
    this.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
}