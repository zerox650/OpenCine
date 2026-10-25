package com.opencapture.monitorui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.opencapture.monitorui.R

/** Local exposure-meter presentation. The host supplies its exercised reading policy and action. */
@Composable
fun MonitorExposureMeterSettings(isOn: Boolean, help: String, onToggle: () -> Unit): Unit {
    MonitorSettingsRowCard(title = stringResource(R.string.monitor_assist_ev_meter)) {
        MonitorSettingsSwitchInlineRow(title = stringResource(R.string.monitor_settings_enabled),
            isOn = isOn, help = help, showTopDivider = false, onToggle = onToggle)
    }
}

/** A known selected look and a local picker action. Catalogs and preparation remain source-owned. */
@Composable
fun MonitorLookSettings(look: String, help: String, onOpen: () -> Unit): Unit {
    MonitorSettingsRowCard(title = "LUT") {
        MonitorSettingsInlineRow(title = "Look", help = help, showTopDivider = false) {
            MonitorSettingsValueText(value = look)
        }
        MonitorSettingsInlineRow(title = "Choose look") {
            MonitorSettingsActionPill(title = "Open", onClick = onOpen)
        }
    }
}
