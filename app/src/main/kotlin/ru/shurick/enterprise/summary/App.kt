package ru.shurick.enterprise.summary

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import ru.shurick.enterprise.summary.notification.worker.HourlyForecastWorker
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val repeatRequest = PeriodicWorkRequestBuilder<HourlyForecastWorker>(
                HourlyForecastWorker.PERIOD,
                HourlyForecastWorker.PERIOD_TIMEUNITS
            )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            HourlyForecastWorker.NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatRequest
        )
    }
}