package ru.shurick.enterprise.city.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shurick.enterprise.city.data.api.AutocompleteCityApi
import ru.shurick.enterprise.city.data.mapper.toEntity
import ru.shurick.enterprise.city.domain.entity.City
import ru.shurick.enterprise.city.domain.repository.AutoCompleteCityRepository
import javax.inject.Inject

class AutoCompleteCityRepositoryImpl @Inject constructor(private val api: AutocompleteCityApi): AutoCompleteCityRepository {

    override suspend fun searchCityByPartName(partName: String, locale: String): List<City> = withContext(Dispatchers.IO) {
        api.searchCityByPartName(
            url = "http://autocomplete.travelpayouts.com/places2",
            partName = partName,
            locale = locale
        ).map { it.toEntity() }
    }
}