package ru.shurick.enterprise.summary.screens.city.other

import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.shurick.enterprise.summary.BuildConfig
import ru.shurick.enterprise.summary.base.mapper.UIMetaMapper
import ru.shurick.enterprise.summary.base.mapper.UITemperatureMapper
import ru.shurick.enterprise.summary.base.model.ResourceData
import ru.shurick.enterprise.summary.base.model.UITotalWeather
import ru.shurick.enterprise.weather.domain.usecase.GetWeatherFromCityLocationUseCase
import ru.shurick.enterprise.weather.domain.usecase.GetWeatherHourlyFromLocationUseCase
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getWeatherFromCityLocationUseCase: GetWeatherFromCityLocationUseCase,
    private val getWeatherHourlyFromLocationUseCase: GetWeatherHourlyFromLocationUseCase,
    private val uiMetaMapper: UIMetaMapper,
    private val uiTemperatureMapper: UITemperatureMapper,
) : ViewModel() {

    val city: String = savedStateHandle.get("cityName")!!

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _weather = MutableStateFlow<ResourceData<UITotalWeather>>(ResourceData.Loading)
    val weather get() = _weather.asStateFlow()

    init {
        rescheduledJob()
    }

    private fun rescheduledJob() {
        if (_weather.value !is ResourceData.Error) {
            _weather.value = ResourceData.Loading
        }

        viewModelScope.launch {

            delay(1000)

            try {
                val current = getWeatherFromCityLocationUseCase(
                    city,
                    "metric",
                    Locale.current.language,
                    BuildConfig.OPEN_WEATHER_API
                )

                val hourly = getWeatherHourlyFromLocationUseCase(
                    current.coordinates.latitude,
                    current.coordinates.longitude,
                    "metric",
                    Locale.current.language,
                    BuildConfig.OPEN_WEATHER_API
                )

                _weather.value = ResourceData.Success(
                    UITotalWeather(
                        uiTemperatureMapper.toUserPriority(current),
                        uiTemperatureMapper.toUserPriority(hourly),
                        uiMetaMapper.toUserPriority(current)
                    )
                )
            } catch (e: Exception) {
                _weather.value = ResourceData.Error(e)
                rescheduledJob()
            }
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        _isRefreshing.value = true
        rescheduledJob()
    }
}