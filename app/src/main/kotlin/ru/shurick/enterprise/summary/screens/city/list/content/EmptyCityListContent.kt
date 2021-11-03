package ru.shurick.enterprise.summary.screens.city.list.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.White200

@Composable
fun EmptyCityListContent() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = stringResource(R.string.add_city_title_text),
            fontWeight = FontWeight.Bold,
            color = Color.White200,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.weight(0.2f))
        Icon(
            painter = painterResource(R.drawable.ic_city),
            contentDescription = null,
            modifier = Modifier.width(300.dp),
            tint = Color.White200
        )
        Spacer(modifier = Modifier.weight(0.2f))
        Text(
            text = stringResource(R.string.add_city_description_text),
            color = Color.White200,
            modifier = Modifier.padding(
                start = Dimens.MARGIN_PADDING_XL,
                end = Dimens.MARGIN_PADDING_XL
            ),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(0.4f))
    }
}