package ru.shurick.enterprise.summary.base.ui.switches

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import ru.shurick.enterprise.storage.MetaUnits
import ru.shurick.enterprise.summary.theme.DarkGray100
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.Gray500
import ru.shurick.enterprise.utils.ui.getIdentifier

fun <T : Enum<T>> Enum<T>.getIdentifierName(): String = name.lowercase()

@Composable
fun Float.toDp(): Dp = with(LocalDensity.current) { this@toDp.toDp() }

@Composable
fun <T : Enum<T>> CustomSwitch(items: List<T>, selected: T, isSelected: (T) -> Unit) {

    var firstItemSize by remember { mutableStateOf(Size.Zero) }
    var secondItemSize by remember { mutableStateOf(Size.Zero) }

    var shiftToSecondItem by remember { mutableStateOf(0F) }
    val spaceBetweenItems = 50F

    val haptic = LocalHapticFeedback.current

    var isAnimated by remember { mutableStateOf(items[0] != selected) }
    val thumbTransition = updateTransition(targetState = isAnimated, label = "thumbTransition")
    val thumbOffset by thumbTransition.animateOffset(
        transitionSpec = { tween(300) },
        label = "thumbOffset"
    ) { animated ->
        if (animated) Offset(shiftToSecondItem, 0f) else Offset(0f, 0f)
    }

    val animDp by animateDpAsState(targetValue = with(LocalDensity.current) {

        (if (isAnimated) secondItemSize.width / 2.8 else firstItemSize.width / 2.8).dp

    }, tween(300))

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.Gray500)
            .padding(Dimens.MARGIN_PADDING_XS)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                isSelected(if (isAnimated) items[0] else items[1])
                isAnimated = !isAnimated
            }
    ) {
        Box(
            modifier = Modifier
                .offset(thumbOffset.x.dp, thumbOffset.y.dp)
                .clip(CircleShape)
                .width(animDp)
                .height(24.dp)
                .background(Color.DarkGray100)
        )
        Row() {
            Text(
                text = stringResource(LocalContext.current.getIdentifier(items[0].getIdentifierName())),
                color = Color.White,
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        shiftToSecondItem = ((coordinates.size.width + spaceBetweenItems) / 2.8F)
                        firstItemSize = coordinates.size.toSize()
                    }
                    .padding(start = Dimens.MARGIN_PADDING_XS, end = Dimens.MARGIN_PADDING_XS)
            )
            Spacer(modifier = Modifier.width(spaceBetweenItems.toDp()))
            Text(
                text = stringResource(LocalContext.current.getIdentifier(items[1].getIdentifierName())),
                color = Color.White,
                modifier = Modifier
                    .onGloballyPositioned { coordinates -> secondItemSize = coordinates.size.toSize() }
                    .padding(start = Dimens.MARGIN_PADDING_XS, end = Dimens.MARGIN_PADDING_XS)
            )
        }
    }
}


@Preview
@Composable
fun Test() {

    CustomSwitch(
        listOf(MetaUnits.MILLIBAR, MetaUnits.MILLIMETERS_MERCURY),
        MetaUnits.MILLIBAR
    ) {

    }
}