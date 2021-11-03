package ru.shurick.enterprise.summary.navigation.graph

import android.Manifest
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.base.ui.screens.PermissionScreen
import ru.shurick.enterprise.summary.navigation.destination.Destination
import ru.shurick.enterprise.summary.screens.city.other.CityWeatherScreen
import ru.shurick.enterprise.summary.screens.main.MainScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GlobalNavigation(activity: Activity) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Main.route) {
        composable(
            route = Destination.Main.route
        ) {
            PermissionScreen(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                rationaleText = stringResource(R.string.permission_location_rationale_text),
                deniedText = stringResource(R.string.permission_location_denied_text),
            ) {
                MainScreen(
                    goToOtherCity = { cityName ->
                        navController.navigate(Destination.Other.createRoute(cityName))
                    },
                    popUpTo = { activity.finish() }
                )
            }
        }
        composable(
            route = Destination.Other.route,
            arguments = listOf(navArgument("cityName") { type = NavType.StringType })
        ) {
            CityWeatherScreen { navController.popBackStack() }
        }
    }
}