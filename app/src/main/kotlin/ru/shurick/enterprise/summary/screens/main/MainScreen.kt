package ru.shurick.enterprise.summary.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.shurick.enterprise.summary.screens.city.current.CurrentCityScreen
import ru.shurick.enterprise.summary.screens.city.list.ListCitiesScreen
import ru.shurick.enterprise.summary.screens.settings.SettingsScreen
import ru.shurick.enterprise.summary.theme.Dimens

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    goToOtherCity: (cityName: String) -> Unit,
    popUpTo: () -> Unit
) {
    val pageCount = 2

    val scope = rememberCoroutineScope()
    val backdropScaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    val pagerState = rememberPagerState()

    BackHandler(true) {

        when {
            backdropScaffoldState.isConcealed -> {
                scope.launch { backdropScaffoldState.reveal() }
            }
            pagerState.currentPage == 1 -> {
                scope.launch { pagerState.animateScrollToPage(0) }
            }
            else -> { popUpTo() }
        }
    }

    BackdropScaffold(
        scaffoldState = backdropScaffoldState,
        peekHeight = Dimens.BACK_LAYER_HEIGHT,
        headerHeight = Dimens.NOUGHT,
        appBar = {},
        backLayerContent = {
            Surface() {
                Column(Modifier.fillMaxSize()) {

                    HorizontalPager(
                        count = pageCount,
                        state = pagerState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                    ) { page ->
                        if (page == 0) {
                            CurrentCityScreen(backLayerState = backdropScaffoldState)
                        } else {
                            ListCitiesScreen { goToOtherCity(it) }
                        }
                    }

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(Dimens.MARGIN_PADDING_M),
                    )
                }
            }
        },
        frontLayerContent = { SettingsScreen() })
}