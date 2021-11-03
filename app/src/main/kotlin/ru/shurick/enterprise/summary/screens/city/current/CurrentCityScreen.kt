package ru.shurick.enterprise.summary.screens.city.current

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import ru.shurick.enterprise.summary.base.model.what
import ru.shurick.enterprise.summary.base.ui.bars.BaseWeatherTopBar
import ru.shurick.enterprise.summary.base.ui.content.items.CurrentWeatherItemReceived
import ru.shurick.enterprise.summary.base.ui.content.items.HourlyWeatherItemsReceived
import ru.shurick.enterprise.summary.base.ui.content.items.MetaInfoItemsReceived
import ru.shurick.enterprise.summary.base.ui.dialogs.ErrorDialog
import ru.shurick.enterprise.summary.base.ui.shimmers.CurrentWeatherItemLoading
import ru.shurick.enterprise.summary.base.ui.shimmers.HourlyWeatherItemsLoading
import ru.shurick.enterprise.summary.base.ui.shimmers.MetaInfoItemsLoading

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun CurrentCityScreen(
    viewModel: CurrentWeatherViewModel = hiltViewModel(),
    backLayerState: BackdropScaffoldState
) {

    val scope = rememberCoroutineScope()

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val state by viewModel.weather.collectAsState()

    Scaffold(
        topBar = {
            BaseWeatherTopBar(
                currentState = state,
                icon = Icons.Default.LocationOn,
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            if (backLayerState.isConcealed) backLayerState.reveal() else backLayerState.conceal()
                        }
                    }) { Icon(Icons.Filled.Settings, contentDescription = null) }
                }
            )
        },
    )
    {
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