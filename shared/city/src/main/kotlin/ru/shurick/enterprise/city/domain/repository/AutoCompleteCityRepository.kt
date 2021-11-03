package ru.shurick.enterprise.city.domain.repository

import ru.shurick.enterprise.city.domain.entity.City

interface AutoCompleteCityRepository {

    suspend fun searchCityByPartName(partName: String, locale: String): List<City>
}