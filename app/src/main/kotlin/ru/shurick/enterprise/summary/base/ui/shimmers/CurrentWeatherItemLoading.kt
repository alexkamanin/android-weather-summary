package ru.shurick.enterprise.summary.base.ui.shimmers

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shurick.enterprise.summary.theme.Dimens

@Composable
fun CurrentWeatherItemLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.MARGIN_PADDING_S),
        ) {
            Box(
                modifier = Modifier
                    .padding(start = Dimens.MARGIN_PADDING_L)
                    .size(28.dp)
                    .roundedPlaceholder(true)
            )
            Box(
                modifier = Modifier
                    .size(245.dp, 24.dp)
                    .padding(start = Dimens.MARGIN_PADDING_XS)
                    .roundedPlaceholder(true)
            )

        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(50.dp, 50.dp)
                .roundedPlaceholder(true)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(150.dp, 24.dp)
                .roundedPlaceholder(true)
        )
        Spacer(modifier = Modifier.height(Dimens.MARGIN_PADDING_L))
    }
}