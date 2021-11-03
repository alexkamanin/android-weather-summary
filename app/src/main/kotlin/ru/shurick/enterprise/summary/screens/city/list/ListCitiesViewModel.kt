package ru.shurick.enterprise.summary.screens.city.list

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.shurick.enterprise.city.domain.entity.City
import ru.shurick.enterprise.city.domain.usecase.SearchCityByPartNameUseCase
import ru.shurick.enterprise.storage.provider.CitiesProvider
import javax.inject.Inject

@HiltViewModel
class ListCitiesViewModel @Inject constructor(
    private val searchCityByPartNameUseCase: SearchCityByPartNameUseCase,
    private val citiesProvider: CitiesProvider,
) : ViewModel() {

    val searchQueryString = MutableStateFlow("")

    private val _cities = MutableStateFlow<List<String>>(listOf())
    val cities
        get() = _cities.asStateFlow()

    private val _expandedSearch = MutableStateFlow(false)
    val expandedSearch
        get() = _expandedSearch.asStateFlow()

    private val _assumedCities = MutableStateFlow<List<City>?>(null)
    val assumedCities
        get() = _assumedCities.asStateFlow()

    val showSearchBarState = MutableStateFlow(false)

    init {
        citiesProvider.saved.onEach { savedCities ->
            savedCities?.let { _cities.value = it.toList() }
        }.launchIn(viewModelScope)

        searchQueryString.onEach {
            if (it.isNotBlank()) {
                _expandedSearch.value = true
                searchCityByPartNameUseCase(it, Locale.current.language).also { cities ->
                    _assumedCities.value = cities
                }
                if (isSpecifiedExactCity()) {
                    _expandedSearch.value = false
                } else {

                }
            } else {
                _expandedSearch.value = false
                _assumedCities.value = null
            }
        }.launchIn(viewModelScope)
    }

    fun onItemCityClick(city: City) {
        searchQueryString.value = city.cityName
        _expandedSearch.value = false
        viewModelScope.launch {
            citiesProvider.addNewCityToSaved(searchQueryString.value)
        }
    }

    fun removeCity(cityName: String) {
        viewModelScope.launch {
            citiesProvider.removeCityFromSaved(cityName)
        }
    }

    fun onSearchClick() {
        if (isSpecifiedExactCity()) {
            viewModelScope.launch {
                _assumedCities.value?.first().let {
                    if (it != null) {
                        citiesProvider.addNewCityToSaved(it.cityName)
                        showSearchBarState.value = false
                    }
                }
            }
        }
    }

    private fun isSpecifiedExactCity(): Boolean {
        return _assumedCities.value?.any { city -> city.cityName.lowercase() == searchQueryString.value.lowercase() } == true
    }
}