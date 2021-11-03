package ru.shurick.enterprise.weather.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shurick.enterprise.weather.data.api.WeatherApi
import ru.shurick.enterprise.weather.data.mapper.toEntity
import ru.shurick.enterprise.weather.data.mapper.toEntityList
import ru.shurick.enterprise.weather.domain.entity.WeatherHourly
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary
import ru.shurick.enterprise.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {

    override suspend fun getWeatherFromCurrentLocation(
        latitude: Double,
        longitude: Double,
        units: String,
        language: String,
        appid: String
    ): WeatherSummary = withContext(Dispatchers.IO) {
        api.getWeatherFromCurrentLocation(latitude, longitude, units, language, appid).toEntity()
    }

    override suspend fun getWeatherFromCityLocation(
        city: String,
        units: String,
        language: String,
        appid: String
    ): WeatherSummary = withContext(Dispatchers.IO) {
        api.getWeatherFromCityLocation(city, units, language, appid).toEntity()
    }

    override suspend fun getWeatherHourlyListFromLocation(
        latitude: Double,
        longitude: Double,
        units: String,
        language: String,
        appid: String
    ): List<WeatherHourly> = withContext(Dispatchers.IO) {
        api.getWeatherHourlyFromLocation(latitude, longitude, units, language, appid).toEntityList()
    }
}