package ru.shurick.enterprise.summary.screens.city.current

import android.annotation.SuppressLint
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.shurick.enterprise.storage.provider.SettingsProvider
import ru.shurick.enterprise.summary.BuildConfig
import ru.shurick.enterprise.summary.base.mapper.UIMetaMapper
import ru.shurick.enterprise.summary.base.mapper.UITemperatureMapper
import ru.shurick.enterprise.summary.base.model.ResourceData
import ru.shurick.enterprise.summary.base.model.UITotalWeather
import ru.shurick.enterprise.utils.location.LocationProvider
import ru.shurick.enterprise.weather.domain.usecase.GetWeatherFromCurrentLocationUseCase
import ru.shurick.enterprise.weather.domain.usecase.GetWeatherHourlyFromLocationUseCase
import javax.inject.Inject

@SuppressLint("MissingPermission")
@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getWeatherFromCurrentLocationUseCase: GetWeatherFromCurrentLocationUseCase,
    private val getWeatherHourlyFromLocationUseCase: GetWeatherHourlyFromLocationUseCase,
    private val settingsProvider: SettingsProvider,
    private val locationProvider: LocationProvider,
    private val uiMetaMapper: UIMetaMapper,
    private val uiTemperatureMapper: UITemperatureMapper
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing get() = _isRefreshing.asStateFlow()

    private val _weather = MutableStateFlow<ResourceData<UITotalWeather>>(ResourceData.Loading)
    val weather get() = _weather.asStateFlow()

    init {
        settingsProvider.saved.onEach { rescheduledJob() }.launchIn(viewModelScope)
    }

    private fun rescheduledJob() {
        if (_weather.value !is ResourceData.Error) {
            _weather.value = ResourceData.Loading
        }

        viewModelScope.launch {
            delay(1000)

            locationProvider.getCurrentLocation(
                success = { location ->

                    try {

                        val current = getWeatherFromCurrentLocationUseCase(
                            location.latitude,
                            location.longitude,
                            "metric",
                            Locale.current.language,
                            BuildConfig.OPEN_WEATHER_API
                        )
                        val hourly = getWeatherHourlyFromLocationUseCase(
                            location.latitude,
                            location.longitude,
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
                },
                failure = {
                    _weather.value = ResourceData.Error(it)
                }
            )
            _isRefreshing.value = false
        }
    }

   fun refresh() {
        _isRefreshing.value = true
        rescheduledJob()
    }
}