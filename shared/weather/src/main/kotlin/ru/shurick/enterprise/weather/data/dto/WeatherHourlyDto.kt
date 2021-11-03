package ru.shurick.enterprise.weather.data.dto

import com.squareup.moshi.Json

data class WeatherHourlyDto(
    val timezone: String,
    val hourly: List<HourlyDto>
)

data class HourlyDto(
    @field:Json(name = "dt")
    val time: Long,
    val temp: Double,
    @field:Json(name = "weather")
    val descriptions: List<Description>
)