package ru.shurick.enterprise.summary.notification.item

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import ru.shurick.enterprise.summary.MainActivity
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.utils.functional.firstUpperCase
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary

fun createHourlyNotification(
    context: Context,
    channelId: String,
    weatherSummary: WeatherSummary
): Notification {

    val icon: Bitmap = Glide
        .with(context)
        .asBitmap()
        .load(weatherSummary.icon)
        .submit()
        .get()

    val text = context.getString(
        R.string.format_notification,
        weatherSummary.temp,
        weatherSummary.feelsLike
    )

    val color = ContextCompat.getColor(context, R.color.yellow_500)

    val contentIntent = PendingIntent.getActivity(
        context,
        0,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_logo)
        .setLargeIcon(icon)
        .setSubText(weatherSummary.city)
        .setContentTitle(weatherSummary.description.firstUpperCase())
        .setContentText(text)
        .setStyle(NotificationCompat.BigTextStyle().bigText(text))
        .setColor(color)
        .setAutoCancel(true)
        .setContentIntent(contentIntent)
        .setCategory(NotificationCompat.CATEGORY_SERVICE)

    return notificationBuilder.build()
}