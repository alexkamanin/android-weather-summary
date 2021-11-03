package ru.shurick.enterprise.summary.base.ui.shimmers

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shurick.enterprise.summary.theme.Dimens

const val COUNT_INITIAL_LOADING_HOURLY_ITEMS = 6

@Composable
fun HourlyWeatherItemsLoading() {
    Row {
        repeat(COUNT_INITIAL_LOADING_HOURLY_ITEMS) {
            Column(
                modifier = Modifier.padding(Dimens.MARGIN_PADDING_S),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp, 22.dp)
                        .roundedPlaceholder(true)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .roundedPlaceholder(true)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp, 22.dp)
                        .roundedPlaceholder(true)
                )
            }
        }
    }
}