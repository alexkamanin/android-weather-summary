package ru.shurick.enterprise.summary.base.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import retrofit2.HttpException
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.theme.Dimens
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Composable
fun ErrorDialog(e: Throwable) {

    val message = when (e) {
        is HttpException -> R.string.error_title_internal to R.string.error_description_internal
        is SocketTimeoutException, is ConnectException, is UnknownHostException, is SocketException -> R.string.error_title_lost_connection to R.string.error_description_lost_connection
        else -> R.string.error_title_unknown to R.string.error_description_internal
    }

    Dialog(onDismissRequest = {}) {
        Card(
            shape = RoundedCornerShape(20),
            backgroundColor = Color.White.copy(alpha = 0.85f)
        ) {
            Row(
                modifier = Modifier.padding(Dimens.MARGIN_PADDING_S),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.WifiOff,
                    contentDescription = null,
                    tint = Color.Red
                )
                Column(modifier = Modifier.padding(Dimens.MARGIN_PADDING_S)) {
                    Text(
                        text = stringResource(message.first),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = stringResource(message.second),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}