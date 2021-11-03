package ru.shurick.enterprise.summary.base.ui.content.items

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.shurick.enterprise.summary.base.model.UIMetaWeather
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.Shapes

@Composable
fun MetaInfoItemsReceived(data: List<UIMetaWeather>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.MARGIN_PADDING_S),
        shape = Shapes.small,
        elevation = 15.dp
    ) {
        Column(modifier = Modifier.padding(Dimens.MARGIN_PADDING_L)) {
            data.forEachIndexed { index, uiMetaWeather ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.align(Alignment.CenterStart)) {
                        Icon(
                            painter = painterResource(id = uiMetaWeather.image),
                            contentDescription = null,
                            tint = uiMetaWeather.color
                        )
                        Text(
                            text = stringResource(uiMetaWeather.description),
                            modifier = Modifier.padding(start = Dimens.MARGIN_PADDING_S)
                        )
                    }
                    Text(
                        text = uiMetaWeather.getString(),
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                if (index != data.size - 1)
                    Divider(
                        modifier = Modifier.padding(
                            top = Dimens.MARGIN_PADDING_S,
                            bottom = Dimens.MARGIN_PADDING_S
                        )
                    )
            }
        }
    }
}