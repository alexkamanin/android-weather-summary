package ru.shurick.enterprise.weather.domain.entity

sealed class WindState(val speed: Double) {

    class North(speed: Double) : WindState(speed)
    class South(speed: Double) : WindState(speed)
    class West(speed: Double) : WindState(speed)
    class East(speed: Double) : WindState(speed)

    class NorthWest(speed: Double) : WindState(speed)
    class SouthWest(speed: Double) : WindState(speed)
    class SouthEast(speed: Double) : WindState(speed)
    class NorthEast(speed: Double) : WindState(speed)

    companion object {
        fun fromDegree(degree: Double, speed: Double): WindState = when (degree) {
            in 22.5..67.5 -> NorthEast(speed)
            in 67.5..112.5 -> East(speed)
            in 112.5..157.5 -> SouthEast(speed)
            in 157.5..202.5 -> South(speed)
            in 202.5..247.5 -> SouthWest(speed)
            in 247.5..292.5 -> West(speed)
            in 292.5..337.5 -> NorthWest(speed)
            else -> North(speed)
        }
    }

    override fun toString(): String = this::class.qualifiedName!!
        .split(".")
        .last()
        .split("(?=\\p{Lu})".toRegex())
        .filter { it.isNotBlank() }
        .joinToString(separator = "_") { it.lowercase() }
}