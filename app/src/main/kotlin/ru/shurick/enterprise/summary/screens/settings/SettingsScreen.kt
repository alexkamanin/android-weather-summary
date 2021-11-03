package ru.shurick.enterprise.summary.screens.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.base.ui.buttons.ToggleButton
import ru.shurick.enterprise.summary.base.ui.switches.CustomSwitch
import ru.shurick.enterprise.summary.theme.Dimens
import ru.shurick.enterprise.summary.theme.Gray400
import ru.shurick.enterprise.summary.theme.Gray700
import ru.shurick.enterprise.summary.theme.Orange500
import ru.shurick.enterprise.utils.ui.checkAllLocationPermissions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    val items by viewModel.items.collectAsState()
    val isNotificationEnabled by viewModel.isNotificationEnabled.collectAsState()

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (context.checkAllLocationPermissions()) {
            viewModel.onClickNotificationButton(true)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = Dimens.NOUGHT
        ) {
            Text(
                text = stringResource(R.string.settings_top_bar_text),
                modifier = Modifier.padding(start = Dimens.MARGIN_PADDING_S)
            )
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(start = Dimens.MARGIN_PADDING_S, end = Dimens.MARGIN_PADDING_S)
        )
        items?.let {
            for (i in it) {
                Row(
                    modifier = Modifier.padding(Dimens.MARGIN_PADDING_S),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(i.title))
                    Spacer(modifier = Modifier.weight(1f))
                    CustomSwitch(i.items, i.selected) { unit -> i.state.value = unit }
                }
            }
            val st = remember {
                mutableStateOf(false)
            }
            Row(
                modifier = Modifier.padding(Dimens.MARGIN_PADDING_S),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.notify_every_hour))
                Spacer(modifier = Modifier.weight(1f))
                ToggleButton(
                    isChecked = isNotificationEnabled,
                    colors = Color.Orange500 to Color.Gray400,
                    icons = Icons.Filled.NotificationsActive to Icons.Default.NotificationsOff
                ) {
                    if (context.checkAllLocationPermissions()) {
                        viewModel.onClickNotificationButton(!isNotificationEnabled)
                    } else {
                        launcher.launch(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }
                }
            }
        }
    }
}