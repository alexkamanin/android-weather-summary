package ru.shurick.enterprise.weather.data.mapper

import android.location.Location
import ru.shurick.enterprise.utils.time.TimeProvider
import ru.shurick.enterprise.weather.BuildConfig
import ru.shurick.enterprise.weather.data.dto.WeatherSummaryDto
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary
import ru.shurick.enterprise.weather.domain.entity.WindState
import kotlin.math.roundToInt

fun WeatherSummaryDto.toEntity() = WeatherSummary(
    temp = indicators.temp.roundToInt(),
    feelsLike = indicators.feelsLike.roundToInt(),
    description = descriptions[0].description,
    icon = String.format(BuildConfig.BACKEND_IMAGE_FORMAT, descriptions[0].icon),
    city = name,
    time = TimeProvider.from(timezone),
    visibility = visibility,
    pressure = indicators.pressure,
    humidity = indicators.humidity,
    sunrise = TimeProvider.from(timezone, system.sunrise),
    sunset = TimeProvider.from(timezone, system.sunset),
    wind = WindState.fromDegree(wind.deg, wind.speed),
    coordinates = Location(name).apply {
        latitude = coordinates.lat
        longitude = coordinates.lon
    }
)