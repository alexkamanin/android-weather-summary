package ru.shurick.enterprise.summary.base.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.base.ui.buttons.PrimaryButton
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.utils.ui.startSettingsActivity

@ExperimentalPermissionsApi
@Composable
fun PermissionScreen(
    permissions: List<String>,
    rationaleText: String,
    deniedText: String,
    granted: @Composable () -> Unit
) {

    val permissionState = rememberMultiplePermissionsState(permissions)

    when {
        permissionState.allPermissionsGranted -> {
            granted()
        }
        permissionState.shouldShowRationale -> {
            val context = LocalContext.current
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = stringResource(R.string.permission_allow_top_bar_text)) }) }
            ) {
                Column {
                    Text(
                        text = deniedText,
                        modifier = Modifier.padding(Dimens.MARGIN_PADDING_XL),
                        textAlign = TextAlign.Justify,
                        lineHeight = 24.sp,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    PrimaryButton(buttonText = stringResource(R.string.permission_allow_go_to_settings_button_text)) {
                        startSettingsActivity(context)
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
        }
        else -> {
            Scaffold(
                topBar = { TopAppBar(title = { Text(text = stringResource(R.string.permission_allow_top_bar_text)) }) }
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = rationaleText,
                        modifier = Modifier.padding(Dimens.MARGIN_PADDING_XL),
                        textAlign = TextAlign.Justify,
                        lineHeight = 24.sp,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    PrimaryButton(buttonText = stringResource(R.string.permission_allow_button_text)) {
                        permissionState.launchMultiplePermissionRequest()
                    }
                    Spacer(modifier = Modifier.weight(0.1f))
                }
            }
        }
    }
}