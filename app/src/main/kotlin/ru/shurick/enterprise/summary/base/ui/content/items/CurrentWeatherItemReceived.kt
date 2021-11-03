package ru.shurick.enterprise.summary.base.ui.content.items

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.weather.domain.entity.WeatherSummary

@Composable
fun CurrentWeatherItemReceived(summary: WeatherSummary) {
    Row {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GlideImage(
                imageModel = summary.icon,
                modifier = Modifier.size(45.dp)
            )
            Text(
                text = summary.description,
                textAlign = TextAlign.Center
            )
        }
    }
    Text(
        text = stringResource(R.string.format_degree, summary.temp),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 70.sp
    )
    Text(
        text = stringResource(R.string.format_feels_like_temp, summary.feelsLike),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(Dimens.MARGIN_PADDING_L))
}