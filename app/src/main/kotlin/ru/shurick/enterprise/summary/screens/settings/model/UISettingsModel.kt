package ru.shurick.enterprise.summary.screens.settings.model

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shurick.enterprise.storage.MetaUnits

data class UISettingsModel(
    @StringRes val title: Int,
    val items: List<MetaUnits>,
    val selected: MetaUnits,
    val state: MutableStateFlow<MetaUnits>
)