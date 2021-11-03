package ru.shurick.enterprise.summary.base.mapper

import android.content.Context
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import ru.shurick.enterprise.storage.MetaUnits
import ru.shurick.enterprise.storage.provider.SettingsProvider
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.base.model.UIMetaWeather
import ru.shurick.enterprise.summary.theme.*
import ru.shurick.enterprise.utils.converter.UnitConverter
import ru.shurick.enterprise.utils.ui.getIdentifier
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary
import ru.shurick.enterprise.weather.domain.entity.WindState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UIMetaMapper @Inject constructor(
    private val settingsProvider: SettingsProvider,
    @ApplicationContext private val context: Context
) {

    suspend fun toUserPriority(weatherSummary: WeatherSummary): List<UIMetaWeather> {

        val currentSetting = settingsProvider.saved.first()

        return with(mutableListOf<UIMetaWeather>()) {
            add(sunriseWithUserPriority(weatherSummary.sunrise))
            add(sunsetWithUserPriority(weatherSummary.sunset))
            add(pressureWithUserPriority(currentSetting.pressure, weatherSummary.pressure))
            add(humidityWithUserPriority(weatherSummary.humidity))
            add(windWithUserPriority(currentSetting.wind, weatherSummary.wind))
            add(visibilityWithUserPriority(currentSetting.visibility, weatherSummary.visibility))
            toList()
        }
    }

    private fun sunriseWithUserPriority(sunrise: String) = UIMetaWeather.NonFormatted(
        R.drawable.ic_sunrise,
        R.string.title_sunrise,
        Color.Orange500,
        sunrise
    )

    private fun sunsetWithUserPriority(sunset: String) = UIMetaWeather.NonFormatted(
        R.drawable.ic_sunset,
        R.string.title_sunset,
        Color.Orange200,
        sunset
    )

    private fun pressureWithUserPriority(
        destinationUnits: MetaUnits,
        currentValue: Int
    ): UIMetaWeather {
        return UIMetaWeather.AloneFormatted(
            R.drawable.ic_pressure,
            R.string.title_pressure,
            Color.Red100,
            if (destinationUnits == MetaUnits.MILLIMETERS_MERCURY) R.string.format_mm_rt_st else R.string.format_millibar,
            if (destinationUnits == MetaUnits.MILLIMETERS_MERCURY) UnitConverter.fromMillibarToMillimetersMercury(
                currentValue
            ) else currentValue
        )
    }

    private fun humidityWithUserPriority(humidity: Int) = UIMetaWeather.AloneFormatted(
        R.drawable.ic_humidity,
        R.string.title_humidity,
        Color.Teal300,
        R.string.format_percent,
        humidity
    )

    private fun windWithUserPriority(destinationUnits: MetaUnits, windState: WindState) =
        UIMetaWeather.ManyFormatted(
            R.drawable.ic_wind,
            R.string.title_wind,
            Color.Teal100,
            if (destinationUnits == MetaUnits.KILOMETER_IN_HOUR) R.string.format_km_h else R.string.format_m_s,
            arrayOf(
                if (destinationUnits == MetaUnits.KILOMETER_IN_HOUR) UnitConverter.fromMeterInSecondToKilometerInHour(
                    windState.speed
                ) else windState.speed,
                with(context) { getString(getIdentifier(windState)) }
            )
        )

    private fun visibilityWithUserPriority(destinationUnits: MetaUnits, visibility: Int) =
        UIMetaWeather.AloneFormatted(
            R.drawable.ic_visibility,
            R.string.title_visibility,
            Color.Green100,
            if (destinationUnits == MetaUnits.KILOMETER) R.string.format_km else R.string.format_m,
            if (destinationUnits == MetaUnits.KILOMETER) UnitConverter.fromMeterToKilometer(
                visibility
            ) else visibility
        )
}