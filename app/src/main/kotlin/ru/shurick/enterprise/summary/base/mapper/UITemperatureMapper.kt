package ru.shurick.enterprise.summary.base.mapper

import kotlinx.coroutines.flow.first
import ru.shurick.enterprise.storage.MetaUnits
import ru.shurick.enterprise.storage.provider.SettingsProvider
import ru.shurick.enterprise.utils.converter.UnitConverter
import ru.shurick.enterprise.weather.domain.entity.WeatherHourly
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UITemperatureMapper @Inject constructor(
    private val settingsProvider: SettingsProvider
) {

    suspend fun toUserPriority(weatherSummary: WeatherSummary): WeatherSummary {

        val currentSetting = settingsProvider.saved.first()

        return if (currentSetting.temperature == MetaUnits.FAHRENHEIT) {
            weatherSummary.copy(
                temp = UnitConverter.fromCelsiusToFahrenheit(weatherSummary.temp),
                feelsLike = UnitConverter.fromCelsiusToFahrenheit(weatherSummary.feelsLike),
            )
        } else {
            weatherSummary
        }
    }

    suspend fun toUserPriority(weatherHourly: List<WeatherHourly>): List<WeatherHourly> {

        val currentSetting = settingsProvider.saved.first()

        return if (currentSetting.temperature == MetaUnits.FAHRENHEIT) {
            weatherHourly.map { it.copy(temp = UnitConverter.fromCelsiusToFahrenheit(it.temp)) }
        } else {
            weatherHourly
        }
    }
}