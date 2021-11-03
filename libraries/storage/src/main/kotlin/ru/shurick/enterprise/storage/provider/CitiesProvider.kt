package ru.shurick.enterprise.storage.provider

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CitiesProvider @Inject constructor(private val cities: DataStore<Preferences>) {

    private val KEY = stringSetPreferencesKey("cities")

    val saved
        get() = cities.data.map { it[KEY] }

    private suspend fun getSavedCities(): Set<String>? = saved.first()

    suspend fun removeCityFromSaved(value: String) = cities.edit { mutablePreferences ->
        getSavedCities()?.let {
            mutablePreferences[KEY] = it.toMutableSet().apply { remove(value) }
        }
    }

    suspend fun addNewCityToSaved(value: String) = cities.edit { mutablePreferences ->
        mutablePreferences[KEY] = getSavedCities()?.let {
            it.toMutableSet().apply {
                add(value)
                toSet()
            }
        } ?: setOf(value)
    }

//    suspend fun setSaved(value: Set<String>) {
//        cities.edit { it[KEY] = value }
//    }
}