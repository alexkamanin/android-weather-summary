package ru.shurick.enterprise.weather.domain.entity

import android.location.Location

data class WeatherSummary(
    val temp: Int,
    val feelsLike: Int,
    val description: String,
    val icon: String,
    val city: String,
    val time: String,
    val visibility: Int,
    val pressure: Int,
    val humidity: Int,
    val sunrise: String,
    val sunset: String,
    val wind: WindState,
    val coordinates: Location
)