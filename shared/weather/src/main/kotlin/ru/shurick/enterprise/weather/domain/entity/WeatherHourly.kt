package ru.shurick.enterprise.weather.domain.entity

//data class WeatherHourly(
////    val timezone: String,
//    val hourly: List<Hourly>
//)

data class WeatherHourly(
    val time: String,
    val temp: Int,
    val icon: String
)