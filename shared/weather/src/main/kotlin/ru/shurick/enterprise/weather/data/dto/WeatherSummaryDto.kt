package ru.shurick.enterprise.weather.data.dto

import com.squareup.moshi.Json

data class WeatherSummaryDto(
    @field:Json(name = "weather")
    val descriptions: List<Description>,
    @field:Json(name = "main")
    val indicators: Indicators,
    val name: String,
    val timezone: Long,
    val wind: Wind,
    val visibility: Int,
    @field:Json(name = "sys")
    val system: Sun,
    @field:Json(name = "coord")
    val coordinates: Coordinates
)

data class Coordinates(
    val lon: Double,
    val lat: Double
)

data class Description(
    val description: String,
    val icon: String
)

data class Indicators(
    val temp: Double,
    @field:Json(name = "feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Double,
    val gust: Double
)

data class Sun(
    val sunrise: Long,
    val sunset: Long
)



