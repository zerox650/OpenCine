package com.opencapture.monitorui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.opencapture.monitorui.R

/** Common touch safety choices with storage and optional domain copy supplied by the host. */
@Composable
public fun MonitorOperatorSafetySettings(
    recordConfirmation: Boolean, haptics: Boolean, keepScreenAwake: Boolean,
    onRecordConfirmation: (Boolean) -> Unit, onHaptics: (Boolean) -> Unit,
    onKeepScreenAwake: (Boolean) -> Unit,
    hapticsHelp: String? = null,
    keepScreenAwakeHelp: String? = null,
    feedback: () -> Unit = {},
) {
    MonitorSettingsRowCard(title = stringResource(R.string.monitor_settings_touch_safety)) {
        MonitorSettingsSwitchInlineRow(
            title = stringResource(R.string.monitor_settings_record_confirmation),
            help = stringResource(R.string.monitor_settings_record_confirmation_help),
            showTopDivider = false, isOn = recordConfirmation,
        ) { feedback(); onRecordConfirmation(!recordConfirmation) }
        MonitorSettingsSwitchInlineRow(
            title = stringResource(R.string.monitor_settings_haptics),
            help = hapticsHelp ?: stringResource(R.string.monitor_settings_haptics_help), isOn = haptics,
        ) {
            feedback(); onHaptics(!haptics)
        }
        MonitorSettingsSwitchInlineRow(
            title = stringResource(R.string.monitor_settings_keep_awake),
            help = keepScreenAwakeHelp ?: stringResource(R.string.monitor_settings_keep_awake_help), isOn = keepScreenAwake,
        ) {
            feedback(); onKeepScreenAwake(!keepScreenAwake)
        }
    }
}

/** A display preference, independently of camera capture settings. */
@Composable
public fun MonitorPanelSettings(hdrDisplay: Boolean, help: String,
    onChange: (Boolean) -> Unit, feedback: () -> Unit = {}) {
    MonitorSettingsRowCard(title = stringResource(R.string.monitor_settings_panel)) {
        MonitorSettingsSwitchInlineRow(
            title = stringResource(R.string.monitor_settings_hdr_display),
            help = help, showTopDivider = false, isOn = hdrDisplay,
        ) {
            feedback(); onChange(!hdrDisplay)
        }
    }
}
