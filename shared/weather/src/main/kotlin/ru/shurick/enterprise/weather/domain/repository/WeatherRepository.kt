package ru.shurick.enterprise.weather.domain.repository

import ru.shurick.enterprise.weather.domain.entity.WeatherHourly
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary

interface WeatherRepository {

    suspend fun getWeatherFromCurrentLocation(
        latitude: Double,
        longitude: Double,
        units: String,
        language: String,
        appid: String
    ): WeatherSummary

    suspend fun getWeatherFromCityLocation(
        city: String,
        units: String,
        language: String,
        appid: String
    ): WeatherSummary

    suspend fun getWeatherHourlyListFromLocation(
        latitude: Double,
        longitude: Double,
        units: String,
        language: String,
        appid: String
    ): List<WeatherHourly>
}