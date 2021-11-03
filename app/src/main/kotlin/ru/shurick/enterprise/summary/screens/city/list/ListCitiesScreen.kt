package ru.shurick.enterprise.summary.screens.city.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import ru.shurick.enterprise.summary.base.ui.animation.enterExpand
import ru.shurick.enterprise.summary.base.ui.animation.enterFadeIn
import ru.shurick.enterprise.summary.base.ui.animation.exitCollapse
import ru.shurick.enterprise.summary.base.ui.animation.exitFadeOut
import ru.shurick.enterprise.summary.screens.city.list.bars.ListCitiesTopBar
import ru.shurick.enterprise.summary.screens.city.list.bars.SearchCitiesTopBar
import ru.shurick.enterprise.summary.screens.city.list.content.EmptyCityListContent
import ru.shurick.enterprise.summary.screens.city.list.items.CityItem
import ru.shurick.enterprise.summary.screens.city.list.items.SearchQueryList
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.Gray400
import ru.shurick.enterprise.summary.theme.White300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ListCitiesScreen(
    viewModel: ListCitiesViewModel = hiltViewModel(),
    goToOtherCity: (cityName: String) -> Unit
) {

    val cities by viewModel.cities.collectAsState()
    val showSearchBarState by viewModel.showSearchBarState.collectAsState()

    val enter by lazy { enterFadeIn + enterExpand }
    val exit by lazy { exitFadeOut + exitCollapse }

    Scaffold(
        topBar = {
            ListCitiesTopBar()
            AnimatedVisibility(
                showSearchBarState,
                enter = enter,
                exit = exit
            ) {
                SearchCitiesTopBar(viewModel) {
                    viewModel.showSearchBarState.value = false
                }
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (cities.isEmpty()) {
                EmptyCityListContent()
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items = cities, key = { item -> item.hashCode() }) { item ->
                        CityItem(
                            cityName = item,
                            onClick = { goToOtherCity(it) },
                            onDismiss = { viewModel.removeCity(item) }
                        )
                    }
                }
            }

            FloatingActionButton(
                onClick = { viewModel.showSearchBarState.value = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(Dimens.MARGIN_PADDING_L),
                backgroundColor = Color.Gray400
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White300)
            }
            SearchQueryList(viewModel) {
                viewModel.showSearchBarState.value = false
            }
        }
    }
}