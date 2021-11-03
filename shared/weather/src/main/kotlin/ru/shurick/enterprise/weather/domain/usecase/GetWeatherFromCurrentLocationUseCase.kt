package ru.shurick.enterprise.weather.domain.usecase

import ru.shurick.enterprise.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherFromCurrentLocationUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        units: String,
        language: String,
        appid: String
    ) = repository.getWeatherFromCurrentLocation(latitude, longitude, units, language, appid)
}