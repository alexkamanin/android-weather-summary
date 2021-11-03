package ru.shurick.enterprise.summary.base.ui.buttons

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shurick.enterprise.summary.theme.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun PrimaryButton(buttonText: String, enabled: Boolean = true, onClick: () -> Unit) {

    val buttonColors = if (isSystemInDarkTheme())
        ButtonDefaults.buttonColors(
            backgroundColor = Color.White200,
            contentColor = Color.Gray400,
            disabledBackgroundColor = Color.DarkGray300,
            disabledContentColor = Color.Gray400
        ) else
        ButtonDefaults.buttonColors(
            backgroundColor = Color.Gray400,
            contentColor = Color.White200,
            disabledBackgroundColor = Color.DarkGray300,
            disabledContentColor = Color.Gray400
        )

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Dimens.MARGIN_PADDING_XL, end = Dimens.MARGIN_PADDING_XL)
            .clip(RoundedCornerShape(12.dp)),
        colors = buttonColors,
        enabled = enabled,
        onClick = { onClick() }
    ) {
        Text(
            modifier = Modifier.padding(
                top = Dimens.MARGIN_PADDING_XS,
                bottom = Dimens.MARGIN_PADDING_XS
            ),
            text = buttonText,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(
    name = "Button in light mode",
)
@Preview(
    name = "Button in dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ButtonPreview() {

    SummaryTheme {
        Column {
            PrimaryButton(buttonText = "Example") {}
            Spacer(modifier = Modifier.height(Dimens.MARGIN_PADDING_S))
            PrimaryButton(buttonText = "Example", false) {}
        }
    }
}