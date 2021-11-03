package ru.shurick.enterprise.summary.base.ui.bars

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shurick.enterprise.summary.base.model.ResourceData
import ru.shurick.enterprise.summary.base.model.UITotalWeather
import ru.shurick.enterprise.summary.base.model.what
import ru.shurick.enterprise.summary.base.ui.shimmers.roundedPlaceholder
import ru.shurick.enterprise.summary.theme.Dimens

@Composable
fun BaseWeatherTopBar(
    currentState: ResourceData<UITotalWeather>,
    icon: ImageVector,
    navigation: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {

    TopAppBar(
        title = {
            Column() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = Dimens.MARGIN_PADDING_XS)
                    )
                    currentState.what(
                        success = { Text(text = it.data.summary.city) },
                        failed = {
                            Box(
                                modifier = Modifier
                                    .size(160.dp, 22.dp)
                                    .roundedPlaceholder(true)
                            )
                        },
                        loading = {
                            Box(
                                modifier = Modifier
                                    .size(160.dp, 22.dp)
                                    .roundedPlaceholder(true)
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                currentState.what(
                    success = { Text(text = it.data.summary.time, fontSize = 12.sp) },
                    failed = {
                        Box(
                            modifier = Modifier
                                .size(178.dp, 16.dp)
                                .roundedPlaceholder(true)
                        )
                    },
                    loading = {
                        Box(
                            modifier = Modifier
                                .size(178.dp, 16.dp)
                                .roundedPlaceholder(true)
                        )
                    }
                )
            }
        },
        navigationIcon = navigation,
        actions = { if (actions != null) { actions() } }
    )
}