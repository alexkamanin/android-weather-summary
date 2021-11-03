package ru.shurick.enterprise.utils.functional

inline fun <T> all(vararg values: T, condition: (T) -> Boolean): Boolean =
    values.all { condition(it) }

fun String.firstUpperCase(): String = if (this.isEmpty()) "" else this.substring(0, 1).uppercase() + this.substring(1)