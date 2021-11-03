package ru.shurick.enterprise.summary.base.model

import ru.shurick.enterprise.weather.domain.entity.WeatherHourly
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary

data class UITotalWeather(
    val summary: WeatherSummary,
    val hourly: List<WeatherHourly>,
    val meta: List<UIMetaWeather>
)