package ru.shurick.enterprise.summary.navigation.destination

sealed class Destination(val route: String) {

    object Main: Destination("MainScreen")

    object Other : Destination("OtherCity/{cityName}") {
        fun createRoute(cityName: String): String = "OtherCity/$cityName"
    }
}