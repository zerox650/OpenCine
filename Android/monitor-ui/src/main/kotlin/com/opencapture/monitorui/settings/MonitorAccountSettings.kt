package com.opencapture.monitorui.settings

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.opencapture.monitorui.MonitorPalette
import com.opencapture.monitorui.R

/** Known local state. The host owns authorization, credentials and request lifetime. */
public enum class MonitorAccountState { NOT_CONFIGURED, CONNECTING, SIGNED_OUT, SIGNED_IN, ATTENTION }

@Composable
public fun MonitorAccountSettings(
    title: String, help: String, state: MonitorAccountState,
    signIn: (() -> Unit)? = null, signOut: (() -> Unit)? = null,
    statusLabel: String? = null, signInTitle: String? = null, signOutTitle: String? = null,
    busyLabel: String? = null, errorMessage: String? = null,
    pillActions: Boolean = false,
) {
    val signInLabel = signInTitle ?: stringResource(R.string.monitor_account_sign_in)
    val signOutLabel = signOutTitle ?: stringResource(R.string.monitor_account_sign_out)
    val notConfiguredLabel = stringResource(R.string.monitor_account_not_configured)
    val signedOutLabel = stringResource(R.string.monitor_account_signed_out)
    val connectedLabel = stringResource(R.string.monitor_account_connected)
    MonitorSettingsRowCard {
        MonitorSettingsInlineRow(title, help, showTopDivider = false) {
            if (busyLabel != null) MonitorSettingsValueText(busyLabel)
            else when (state) {
                MonitorAccountState.NOT_CONFIGURED -> MonitorSettingsValueText(statusLabel ?: notConfiguredLabel)
                MonitorAccountState.CONNECTING -> if (statusLabel != null) MonitorSettingsValueText(statusLabel)
                else CircularProgressIndicator(modifier = Modifier.size(18.dp), color = MonitorPalette.accent, strokeWidth = 2.dp)
                MonitorAccountState.SIGNED_OUT, MonitorAccountState.ATTENTION -> if (signIn != null) {
                    if (pillActions) MonitorSettingsActionPill(signInLabel, onClick = signIn)
                    else MonitorSettingsTextAction(signInLabel, onClick = signIn)
                } else MonitorSettingsValueText(statusLabel ?: signedOutLabel)
                MonitorAccountState.SIGNED_IN -> if (signOut != null) {
                    if (pillActions) MonitorSettingsActionPill(signOutLabel, onClick = signOut)
                    else MonitorSettingsTextAction(signOutLabel, onClick = signOut)
                } else MonitorSettingsValueText(statusLabel ?: connectedLabel)
            }
        }
        errorMessage?.let { Text(it, color = MonitorPalette.accent) }
    }
}
