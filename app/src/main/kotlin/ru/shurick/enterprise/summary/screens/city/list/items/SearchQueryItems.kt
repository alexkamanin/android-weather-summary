package ru.shurick.enterprise.summary.screens.city.list.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.screens.city.list.ListCitiesViewModel
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.White200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchQueryList(viewModel: ListCitiesViewModel, close: () -> Unit) {

    val expanded by viewModel.expandedSearch.collectAsState()
    val cities by viewModel.assumedCities.collectAsState()

    AnimatedVisibility(visible = expanded) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp))
                .background(Color.White200)
        ) {
            if (cities != null) {
                items(items = cities!!) { city ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                viewModel.onItemCityClick(city)
                                close()
                            }
                            .padding(Dimens.MARGIN_PADDING_S)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = buildAnnotatedString {
                                append(
                                    AnnotatedString(
                                        text =  city.cityName,
                                        spanStyle = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.DarkGray
                                        )
                                    )
                                )
                                append(
                                    AnnotatedString(
                                        text = stringResource(R.string.search_cities_item_delimiter),
                                        spanStyle = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Gray
                                        )
                                    )
                                )
                                append(
                                    AnnotatedString(
                                        text = city.countryName,
                                        spanStyle = SpanStyle(color = Color.Gray)
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}