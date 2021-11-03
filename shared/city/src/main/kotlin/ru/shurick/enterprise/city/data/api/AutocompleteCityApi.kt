package ru.shurick.enterprise.city.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import ru.shurick.enterprise.city.data.dto.CityDto

interface AutocompleteCityApi {

    @GET
    suspend fun searchCityByPartName(
        @Url url: String,
        @Query("term") partName: String,
        @Query("locale") locale: String,
        @Query("types[]") type: String = "city"
    ): List<CityDto>
}