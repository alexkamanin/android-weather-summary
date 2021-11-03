package ru.shurick.enterprise.city.data.mapper

import ru.shurick.enterprise.city.data.dto.CityDto
import ru.shurick.enterprise.city.domain.entity.City

fun CityDto.toEntity() = City(cityName, countryName)