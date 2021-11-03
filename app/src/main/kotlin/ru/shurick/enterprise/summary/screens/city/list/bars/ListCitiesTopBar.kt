package ru.shurick.enterprise.summary.screens.city.list.bars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.theme.Dimens

@Composable
fun ListCitiesTopBar() {

    TopAppBar {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = Dimens.MARGIN_PADDING_S)
        ) {
            Icon(Icons.Default.Star, null)
            Spacer(modifier = Modifier.width(Dimens.MARGIN_PADDING_S))
            Text(text = stringResource(R.string.favorites_top_bar_text))
        }
    }
}