package com.opencapture.monitorui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.opencapture.monitorui.MonitorPalette
import com.opencapture.monitorui.R

/** The host supplies cache scope, current IO state and actions. */
@Composable
public fun MonitorCacheSettings(
    cacheOriginals: Boolean? = null, sizeLabel: String, clearing: Boolean = false,
    cacheHelp: String = "", scopeHelp: String, clearHelp: String,
    onCacheOriginals: ((Boolean) -> Unit)? = null, clear: () -> Unit, feedback: () -> Unit = {},
) {
    MonitorSettingsRowCard {
        if (cacheOriginals != null && onCacheOriginals != null) {
            MonitorSettingsSwitchInlineRow(
                title = stringResource(R.string.monitor_storage_full_resolution), help = cacheHelp,
                showTopDivider = false, isOn = cacheOriginals,
            ) { feedback(); onCacheOriginals(!cacheOriginals) }
        }
        MonitorSettingsInlineRow(stringResource(R.string.monitor_storage_local_cache), scopeHelp,
            showTopDivider = cacheOriginals != null && onCacheOriginals != null) {
            MonitorSettingsValueText(sizeLabel)
        }
        MonitorSettingsInlineRow(stringResource(R.string.monitor_storage_clear_cache), clearHelp) {
            if (clearing) MonitorSettingsValueText(stringResource(R.string.monitor_storage_clearing))
            else MonitorSettingsTextAction(stringResource(R.string.monitor_storage_clear), color = MonitorPalette.recording, onClick = clear)
        }
    }
}

/** App identity and protocol descriptions are supplied as content. */
@Composable
public fun MonitorAppInformationSettings(
    theme: String, protocolName: String, version: String,
    themeHelp: String, protocolHelp: String, versionHelp: String,
) {
    MonitorSettingsRowCard(title = stringResource(R.string.monitor_info_title)) {
        MonitorSettingsInlineRow(stringResource(R.string.monitor_info_theme), themeHelp, showTopDivider = false) {
            MonitorSettingsValueText(theme)
        }
        MonitorSettingsInlineRow(stringResource(R.string.monitor_info_protocol), protocolHelp) {
            MonitorSettingsValueText(protocolName)
        }
        MonitorSettingsInlineRow(stringResource(R.string.monitor_info_version), versionHelp) {
            MonitorSettingsValueText(version)
        }
    }
}

/** App-owned document and external navigation actions. */
@Composable
public fun MonitorProjectLegalSettings(
    sourceHelp: String, termsHelp: String,
    source: () -> Unit, privacy: () -> Unit, terms: () -> Unit,
    licenses: (() -> Unit)? = null, notice: (() -> Unit)? = null,
) {
    val sourceLabel = stringResource(R.string.monitor_legal_source_a11y)
    val privacyLabel = stringResource(R.string.monitor_legal_privacy_a11y)
    val termsLabel = stringResource(R.string.monitor_legal_terms_a11y)
    val licensesLabel = stringResource(R.string.monitor_legal_licenses_a11y)
    val noticeLabel = stringResource(R.string.monitor_legal_notice_a11y)
    val openLabel = stringResource(R.string.monitor_action_open)
    MonitorSettingsRowCard(title = stringResource(R.string.monitor_legal_title)) {
        MonitorSettingsInlineRow(stringResource(R.string.monitor_legal_source), sourceHelp, showTopDivider = false) {
            MonitorSettingsActionPill(openLabel, onClick = source,
                modifier = Modifier.semantics { contentDescription = sourceLabel })
        }
        MonitorSettingsInlineRow(
            stringResource(R.string.monitor_legal_privacy),
            stringResource(R.string.monitor_legal_privacy_help),
        ) {
            MonitorSettingsActionPill(openLabel, onClick = privacy,
                modifier = Modifier.semantics { contentDescription = privacyLabel })
        }
        MonitorSettingsInlineRow(stringResource(R.string.monitor_legal_terms), termsHelp) {
            MonitorSettingsActionPill(openLabel, onClick = terms,
                modifier = Modifier.semantics { contentDescription = termsLabel })
        }
        licenses?.let { action ->
            MonitorSettingsInlineRow(
                stringResource(R.string.monitor_legal_licenses),
                stringResource(R.string.monitor_legal_licenses_help),
            ) {
                MonitorSettingsActionPill(openLabel, onClick = action,
                    modifier = Modifier.semantics { contentDescription = licensesLabel })
            }
        }
        notice?.let { action ->
            MonitorSettingsInlineRow(
                stringResource(R.string.monitor_legal_notice),
                stringResource(R.string.monitor_legal_notice_help),
            ) {
                MonitorSettingsActionPill(openLabel, onClick = action,
                    modifier = Modifier.semantics { contentDescription = noticeLabel })
            }
        }
    }
}
