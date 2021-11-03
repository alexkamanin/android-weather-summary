package ru.shurick.enterprise.summary.base.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

sealed class UIMetaWeather(
    @DrawableRes val image: Int,
    @StringRes val description: Int,
    val color: Color
) {

    class NonFormatted(
        @DrawableRes image: Int,
        @StringRes description: Int,
        color: Color,
        val value: Any
    ) : UIMetaWeather(image, description, color)

    class AloneFormatted(
        @DrawableRes image: Int,
        @StringRes description: Int,
        color: Color,
        @StringRes val formatData: Int,
        val value: Any
    ) : UIMetaWeather(image, description, color)

    class ManyFormatted(
        @DrawableRes image: Int,
        @StringRes description: Int,
        color: Color,
        @StringRes val formatData: Int,
        val values: Array<out Any>
    ) : UIMetaWeather(image, description, color)

    @Composable
    fun getString(): String = when (this) {
        is NonFormatted -> value.toString()
        is AloneFormatted -> stringResource(formatData, value)
        is ManyFormatted -> stringResource(formatData, formatArgs = values)
    }
}