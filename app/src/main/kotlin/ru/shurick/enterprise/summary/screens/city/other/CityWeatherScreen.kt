package ru.shurick.enterprise.summary.screens.city.other

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.shurick.enterprise.summary.base.model.what
import ru.shurick.enterprise.summary.base.ui.bars.BaseWeatherTopBar
import ru.shurick.enterprise.summary.base.ui.content.items.CurrentWeatherItemReceived
import ru.shurick.enterprise.summary.base.ui.content.items.HourlyWeatherItemsReceived
import ru.shurick.enterprise.summary.base.ui.content.items.MetaInfoItemsReceived
import ru.shurick.enterprise.summary.base.ui.dialogs.ErrorDialog
import ru.shurick.enterprise.summary.base.ui.shimmers.CurrentWeatherItemLoading
import ru.shurick.enterprise.summary.base.ui.shimmers.HourlyWeatherItemsLoading
import ru.shurick.enterprise.summary.base.ui.shimmers.MetaInfoItemsLoading

@Composable
fun CityWeatherScreen(
    viewModel: CityWeatherViewModel = hiltViewModel(),
    popUpTo: () -> Unit
) {

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val state by viewModel.weather.collectAsState()

    Scaffold(
        topBar = {
            BaseWeatherTopBar(
                currentState = state,
                icon = Icons.Default.Star,
                navigation = {
                    IconButton(onClick = { popUpTo() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.refresh() },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    state.what(
                        success = {
                            CurrentWeatherItemReceived(it.data.summary)
                            HourlyWeatherItemsReceived(it.data.hourly)
                            MetaInfoItemsReceived(it.data.meta)
                        },
                        failed = {
                            ErrorDialog(it.exception)
                            CurrentWeatherItemLoading()
                            HourlyWeatherItemsLoading()
                            MetaInfoItemsLoading()
                        },
                        loading = {
                            CurrentWeatherItemLoading()
                            HourlyWeatherItemsLoading()
                            MetaInfoItemsLoading()
                        }
                    )
                }
            }
        }
    }
}