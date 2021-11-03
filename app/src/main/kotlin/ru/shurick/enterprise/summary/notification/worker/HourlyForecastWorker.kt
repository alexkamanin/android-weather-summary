package ru.shurick.enterprise.summary.notification.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import ru.shurick.enterprise.storage.provider.SettingsProvider
import ru.shurick.enterprise.summary.BuildConfig
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.notification.item.createHourlyNotification
import ru.shurick.enterprise.utils.location.LocationProvider
import ru.shurick.enterprise.utils.ui.checkAllLocationPermissions
import ru.shurick.enterprise.weather.domain.usecase.GetWeatherFromCurrentLocationUseCase
import java.util.concurrent.TimeUnit

@HiltWorker
class HourlyForecastWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val locationProvider: LocationProvider,
    private val settingsProvider: SettingsProvider,
    private val getWeatherFromCurrentLocationUseCase: GetWeatherFromCurrentLocationUseCase
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val NAME = "HourlyForecastWorker"
        const val PERIOD = 1L
        val PERIOD_TIMEUNITS = TimeUnit.HOURS
    }

    private val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = context
        .resources
        .getString(R.string.notification_channel_hourly_weather_id)
    private val channelName = context
        .resources
        .getString(R.string.notification_channel_hourly_weather_name)

    init {
        initHourlyForecastChannel()
    }

    override suspend fun doWork(): Result {

        try {
            if (context.checkAllLocationPermissions() && settingsProvider.saved.first().isNotificationEnabled) {

                locationProvider.getCurrentLocation(
                    success = {
                        getWeatherFromCurrentLocationUseCase(
                            latitude = it.latitude,
                            longitude = it.longitude,
                            units = "metric",
                            language = Locale.current.language,
                            appid = BuildConfig.OPEN_WEATHER_API
                        ).also { summary ->
                            notificationManager.notify(
                                summary.time.hashCode(),
                                createHourlyNotification(context, channelId, summary)
                            )
                        }
                    },
                    failure = { throw it }
                )
            }
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }

    private fun initHourlyForecastChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}