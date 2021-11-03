package ru.shurick.enterprise.utils.converter

import kotlin.math.roundToInt

object UnitConverter {

    fun fromCelsiusToFahrenheit(value: Int): Int = (value * 1.8).roundToInt() + 32

    fun fromMillibarToMillimetersMercury(value: Int): Int = (value * 0.750064).roundToInt()

    fun fromMeterToKilometer(value: Int): Float = value / 1000F

    fun fromMeterInSecondToKilometerInHour(value: Double) = value * 3.6
}