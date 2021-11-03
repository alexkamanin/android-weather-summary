package ru.shurick.enterprise.city.domain.usecase

import ru.shurick.enterprise.city.domain.entity.City
import ru.shurick.enterprise.city.domain.repository.AutoCompleteCityRepository
import javax.inject.Inject

class SearchCityByPartNameUseCase @Inject constructor(private val repository: AutoCompleteCityRepository) {

    suspend operator fun invoke(partName: String, locale: String): List<City> = repository.searchCityByPartName(partName, locale)
}