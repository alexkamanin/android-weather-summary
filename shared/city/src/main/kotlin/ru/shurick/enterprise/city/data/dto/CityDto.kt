package ru.shurick.enterprise.city.data.dto

import com.squareup.moshi.Json

data class CityDto(
    @field:Json(name = "name")
    val cityName: String,
    @field:Json(name = "country_name")
    val countryName: String
)