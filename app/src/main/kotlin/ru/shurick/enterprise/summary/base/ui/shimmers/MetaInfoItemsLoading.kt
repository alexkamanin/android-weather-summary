package ru.shurick.enterprise.summary.base.ui.shimmers

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.Shapes

const val COUNT_INITIAL_LOADING_META_INFO_ITEMS = 6
val LAST_INITIAL_LOADING_META_INFO_ITEM get() = COUNT_INITIAL_LOADING_META_INFO_ITEMS - 1

@Composable
fun MetaInfoItemsLoading() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.MARGIN_PADDING_S),
        shape = Shapes.small,
        elevation = 15.dp
    ) {
        Column(modifier = Modifier.padding(Dimens.MARGIN_PADDING_L)) {
            repeat(COUNT_INITIAL_LOADING_META_INFO_ITEMS) { item ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.align(Alignment.CenterStart)) {
                        Box(
                            modifier = Modifier
                                .size(24.dp, 24.dp)
                                .roundedPlaceholder(true)
                        )
                        Box(
                            modifier = Modifier
                                .size(100.dp, 24.dp)
                                .padding(start = Dimens.MARGIN_PADDING_S)
                                .roundedPlaceholder(true)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(60.dp, 24.dp)
                            .align(Alignment.CenterEnd)
                            .roundedPlaceholder(true)
                    )
                }
                if (item != LAST_INITIAL_LOADING_META_INFO_ITEM) {
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
}