package ru.shurick.enterprise.storage.provider

import androidx.datastore.core.DataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import ru.shurick.enterprise.storage.MetaUnits
import ru.shurick.enterprise.storage.SettingsStore
import ru.shurick.enterprise.utils.functional.all
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class SettingsProvider @Inject constructor(private val settings: DataStore<SettingsStore>) {

    init {
        GlobalScope.launch(Dispatchers.IO) {
            settings.data.collect {
                all(
                    it.pressure,
                    it.temperature,
                    it.wind,
                    it.visibility
                ) { unit -> unit == MetaUnits.UNKNOWN }.also { check ->
                    if (check) initValues()
                }
            }
        }
    }

    val saved get() = settings.data

    suspend fun updateTemperature(unit: MetaUnits) = withContext(Dispatchers.IO) {
        settings.updateData { it.toBuilder().setTemperature(unit).build() }
    }

    suspend fun updatePressure(unit: MetaUnits) = withContext(Dispatchers.IO) {
        settings.updateData { it.toBuilder().setPressure(unit).build() }
    }

    suspend fun updateWind(unit: MetaUnits) = withContext(Dispatchers.IO) {
        settings.updateData { it.toBuilder().setWind(unit).build() }
    }

    suspend fun updateVisibility(unit: MetaUnits) = withContext(Dispatchers.IO) {
        settings.updateData { it.toBuilder().setVisibility(unit).build() }
    }

    suspend fun updateNotification(enabled: Boolean) = withContext(Dispatchers.IO) {
        settings.updateData { it.toBuilder().setIsNotificationEnabled(enabled).build() }
    }

    private suspend fun initValues() = settings.updateData {
        it.toBuilder().apply {
            pressure = MetaUnits.MILLIMETERS_MERCURY
            temperature = MetaUnits.CELSIUS
            wind = MetaUnits.METER_IN_SECOND
            visibility = MetaUnits.KILOMETER
        }.build()
    }
}