package ru.shurick.enterprise.summary.base.ui.shimmers

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import ru.shurick.enterprise.summary.theme.White300

fun Modifier.roundedPlaceholder(visible: Boolean) = composed {
    placeholder(
        color = Color.White300,
        shape = CircleShape,
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}
