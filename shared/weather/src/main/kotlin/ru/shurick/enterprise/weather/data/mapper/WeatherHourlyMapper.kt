package ru.shurick.enterprise.weather.data.mapper

import ru.shurick.enterprise.utils.time.TimeProvider
import ru.shurick.enterprise.weather.BuildConfig
import ru.shurick.enterprise.weather.data.dto.WeatherHourlyDto
import ru.shurick.enterprise.weather.domain.entity.WeatherHourly
import kotlin.math.roundToInt

fun WeatherHourlyDto.toEntityList(): List<WeatherHourly> = hourly.map {
    WeatherHourly(
        time = TimeProvider.from(timezone, it.time),
        temp = it.temp.roundToInt(),
        icon = String.format(BuildConfig.BACKEND_IMAGE_FORMAT, it.descriptions[0].icon)
    )
}