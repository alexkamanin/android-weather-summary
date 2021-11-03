package ru.shurick.enterprise.summary.base.ui.content.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.weather.domain.entity.WeatherHourly

@Composable
fun HourlyWeatherItemsReceived(data: List<WeatherHourly>) {
    LazyRow {
        items(data) { item ->
            Column(
                modifier = Modifier.padding(Dimens.MARGIN_PADDING_S),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.time)
                GlideImage(
                    imageModel = item.icon,
                    modifier = Modifier.size(45.dp)
                )
                Text(text = stringResource(R.string.format_degree, item.temp))
            }
        }
    }
}