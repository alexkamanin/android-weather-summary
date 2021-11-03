package ru.shurick.enterprise.summary.screens.city.list.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.Red500
import ru.shurick.enterprise.summary.theme.White300

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityItem(cityName: String, onClick: (String) -> Unit, onDismiss: (String) -> Unit) {

    val state = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismiss(cityName)
                return@rememberDismissState true
            }
            return@rememberDismissState false
        }
    )

    SwipeToDismiss(
        state = state,
        background = {
            if (state.dismissDirection == DismissDirection.EndToStart) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red500)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = Color.White300,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(Dimens.MARGIN_PADDING_S)
                            .align(Alignment.CenterEnd)
                    )
                }
            }
        },
        directions = setOf(DismissDirection.EndToStart)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .clickable {
                    onClick(cityName)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = cityName,
                modifier = Modifier.padding(
                    start = Dimens.MARGIN_PADDING_XL,
                    top = Dimens.MARGIN_PADDING_M,
                    bottom = Dimens.MARGIN_PADDING_M
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Filled.NavigateNext,
                null,
                modifier = Modifier.padding(end = Dimens.MARGIN_PADDING_L)
            )
        }
    }
}