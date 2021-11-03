package ru.shurick.enterprise.summary.base.ui.buttons

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    icons: Pair<ImageVector, ImageVector>,
    colors: Pair<Color, Color>,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    IconToggleButton(
        checked = isChecked,
        modifier = Modifier.indication(MutableInteractionSource(),null),
        onCheckedChange = { onClick() }
    ) {
        val transition = updateTransition(isChecked, label = "Checked indicator")

        val tint by transition.animateColor(label = "Tint") { isChecked ->
            if (isChecked) colors.first else colors.second
        }

        val size by transition.animateDp(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    keyframes {
                        durationMillis = 250
                        30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                        35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                        40.dp at 75 // ms
                        35.dp at 150 // ms
                    }
                } else {
                    spring(stiffness = Spring.StiffnessVeryLow)
                }
            },
            label = "Size",
            targetValueByState = { 30.dp }
        )

        Icon(
            imageVector = if (isChecked) icons.first else icons.second,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}