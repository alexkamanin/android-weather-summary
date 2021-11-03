package ru.shurick.enterprise.weather.domain.usecase

import ru.shurick.enterprise.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherFromCityLocationUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend operator fun invoke(
        city: String,
        units: String,
        language: String,
        appid: String
    ) = repository.getWeatherFromCityLocation(city, units, language, appid)
}