package ru.shurick.enterprise.summary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.shurick.enterprise.summary.theme.SummaryTheme
import ru.shurick.enterprise.summary.navigation.graph.GlobalNavigation

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {

            SummaryTheme {

                val systemUiController = rememberSystemUiController()

                systemUiController.apply {
                    setStatusBarColor(
                        color = MaterialTheme.colors.primary,
                        darkIcons = false
                    )
                    setNavigationBarColor(
                        color = MaterialTheme.colors.background
                    )
                }
                GlobalNavigation(this)
            }
        }
    }
}