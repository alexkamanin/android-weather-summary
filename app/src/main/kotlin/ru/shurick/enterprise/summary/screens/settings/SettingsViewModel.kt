package ru.shurick.enterprise.summary.screens.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.shurick.enterprise.storage.MetaUnits
import ru.shurick.enterprise.storage.provider.SettingsProvider
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.screens.settings.model.UISettingsModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsProvider: SettingsProvider
) : ViewModel() {

    private val temperature = MutableStateFlow(MetaUnits.UNKNOWN)
    private val pressure = MutableStateFlow(MetaUnits.UNKNOWN)
    private val wind = MutableStateFlow(MetaUnits.UNKNOWN)
    private val visibility = MutableStateFlow(MetaUnits.UNKNOWN)

    private val _items = MutableStateFlow<List<UISettingsModel>?>(null)
    val items: StateFlow<List<UISettingsModel>?>
        get() = _items.asStateFlow()

    private val _isNotificationEnabled = MutableStateFlow(false)
    val isNotificationEnabled get() = _isNotificationEnabled.asStateFlow()

    init {
        initData()
        initObservers()
    }

    private fun initData() {
        viewModelScope.launch {

            settingsProvider.saved.first().let {
                temperature.value = it.temperature
                pressure.value = it.pressure
                wind.value = it.wind
                visibility.value = it.visibility
                _isNotificationEnabled.value = it.isNotificationEnabled
            }

            _items.value = listOf(
                UISettingsModel(
                    R.string.temperature,
                    listOf(MetaUnits.CELSIUS, MetaUnits.FAHRENHEIT),
                    temperature.value,
                    temperature
                ),
                UISettingsModel(
                    R.string.pressure,
                    listOf(MetaUnits.MILLIMETERS_MERCURY, MetaUnits.MILLIBAR),
                    pressure.value,
                    pressure
                ),
                UISettingsModel(
                    R.string.wind,
                    listOf(MetaUnits.KILOMETER_IN_HOUR, MetaUnits.METER_IN_SECOND),
                    wind.value,
                    wind
                ),
                UISettingsModel(
                    R.string.visibility,
                    listOf(MetaUnits.KILOMETER, MetaUnits.METER),
                    visibility.value,
                    visibility
                ),
            )
        }
    }

    private fun initObservers() {
        temperature.onEach { settingsProvider.updateTemperature(it) }.launchIn(viewModelScope)
        pressure.onEach { settingsProvider.updatePressure(it) }.launchIn(viewModelScope)
        wind.onEach { settingsProvider.updateWind(it) }.launchIn(viewModelScope)
        visibility.onEach { settingsProvider.updateVisibility(it) }.launchIn(viewModelScope)
    }

    fun onClickNotificationButton(enabled: Boolean) {
        viewModelScope.launch {
            settingsProvider.updateNotification(enabled)
            _isNotificationEnabled.value = enabled
        }
    }
}