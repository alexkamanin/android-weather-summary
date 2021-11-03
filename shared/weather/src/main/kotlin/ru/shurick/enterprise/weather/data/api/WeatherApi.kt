package ru.shurick.enterprise.weather.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.shurick.enterprise.weather.data.dto.WeatherHourlyDto
import ru.shurick.enterprise.weather.data.dto.WeatherSummaryDto
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherFromCurrentLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("appid") appid: String
    ): WeatherSummaryDto

    @GET("weather")
    suspend fun getWeatherFromCityLocation(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("appid") appid: String
    ): WeatherSummaryDto

    @GET("onecall")
    suspend fun getWeatherHourlyFromLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("appid") appid: String,
        @Query("exclude") exclude: String = "current,alerts"
    ): WeatherHourlyDto
}