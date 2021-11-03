package ru.shurick.enterprise.utils.time

import java.text.SimpleDateFormat
import java.util.*

object TimeProvider {

    private val locale = Locale.getDefault()

    private val localTimeFormat = SimpleDateFormat("HH:mm", locale)
    private val localDateFormat = SimpleDateFormat("E, d MMM yyyy, HH:mm:ss", locale)
    private val otherTimeFormat = SimpleDateFormat("HH:mm")

    fun from(timeZone: String, timestamp: Long): String {
        localTimeFormat.timeZone = TimeZone.getTimeZone(timeZone)
        return localTimeFormat.format(Date(timestamp * 1000))
    }

    fun from(timeZone: Long): String {
        val d = Date()
        val utc = d.time + d.timezoneOffset * 60000
        return localDateFormat.format(Date(utc + (1000 * timeZone)))
    }

    fun from(timeZone: Long, timestamp: Long): String {
        val d = Date()
        val time = timestamp * 1000 + d.timezoneOffset * 60000 + 1000 * timeZone
        return otherTimeFormat.format(Date(time))
    }
}