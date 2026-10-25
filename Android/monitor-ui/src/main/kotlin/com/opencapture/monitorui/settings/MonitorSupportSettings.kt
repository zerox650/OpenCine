package com.opencapture.monitorui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.opencapture.monitorui.R

/** Optional queued report values from an app-owned reporting workflow. */
public data class MonitorReportStatus(val label: String, val detail: String, val id: String? = null)

/** A focused action from the host's real support workflow. */
public data class MonitorSupportAction(
    val id: String, val title: String, val help: String? = null, val actionTitle: String,
    val accessibilityLabel: String, val enabled: Boolean = true, val action: () -> Unit,
)

@Composable
public fun MonitorSupportSettings(
    automaticReports: Boolean? = null, available: Boolean = false, automaticHelp: String = "", unavailableHelp: String = "",
    onAutomaticReports: ((Boolean) -> Unit)? = null, report: () -> Unit, privacy: (() -> Unit)? = null,
    status: MonitorReportStatus? = null, discard: (() -> Unit)? = null, feedback: () -> Unit = {},
    reportTitle: String? = null,
    reportHelp: String? = null,
    reportActionTitle: String? = null, actions: List<MonitorSupportAction> = emptyList(),
) {
    val title = reportTitle ?: stringResource(R.string.monitor_support_report_problem)
    val help = reportHelp ?: stringResource(R.string.monitor_support_report_problem_help)
    val actionTitle = reportActionTitle ?: stringResource(R.string.monitor_action_open)
    MonitorSettingsRowCard(title = stringResource(R.string.monitor_support_help_feedback)) {
        MonitorSettingsInlineRow(title, help, showTopDivider = false) {
            MonitorSettingsActionPill(actionTitle, onClick = report,
                modifier = Modifier.testTag("support.report.open").semantics { contentDescription = title })
        }
        actions.forEach { action ->
            MonitorSettingsInlineRow(action.title, action.help) {
                MonitorSettingsActionPill(action.actionTitle, enabled = action.enabled, onClick = action.action,
                    modifier = Modifier.testTag(action.id).semantics { contentDescription = action.accessibilityLabel })
            }
        }
        if (status != null) {
            MonitorSettingsInlineRow(stringResource(R.string.monitor_support_report_status), status.detail) {
                MonitorSettingsValueText(status.label)
            }
            status.id?.let { id ->
                MonitorSettingsInlineRow(
                    stringResource(R.string.monitor_support_report_id),
                    stringResource(R.string.monitor_support_report_id_help),
                ) { MonitorSettingsValueText(id) }
            }
            if (discard != null) {
                MonitorSettingsInlineRow(
                    stringResource(R.string.monitor_support_queued_report),
                    stringResource(R.string.monitor_support_queued_report_help),
                ) {
                    MonitorSettingsActionPill(stringResource(R.string.monitor_support_discard), onClick = discard)
                }
            }
        }
        if (automaticReports != null) {
            val label = stringResource(R.string.monitor_support_automatic_reports)
            if (available && onAutomaticReports != null) {
                MonitorSettingsSwitchInlineRow(label, automaticReports, automaticHelp) {
                    feedback(); onAutomaticReports(!automaticReports)
                }
            } else {
                MonitorSettingsInlineRow(label, unavailableHelp) {
                    MonitorSettingsValueText(stringResource(R.string.monitor_support_off))
                }
            }
        }
        privacy?.let { action ->
            MonitorSettingsInlineRow(
                stringResource(R.string.monitor_support_reporting_privacy),
                stringResource(R.string.monitor_support_reporting_privacy_help),
            ) {
                MonitorSettingsActionPill(stringResource(R.string.monitor_support_read), onClick = action)
            }
        }
    }
}

@Composable
public fun MonitorDiagnosticSettings(
    expanded: Boolean, onExpanded: (Boolean) -> Unit, deleteHelp: String,
    save: () -> Unit, delete: () -> Unit,
) {
    MonitorSettingsGroupCard(
        title = stringResource(R.string.monitor_support_diagnostic_options),
        caption = stringResource(R.string.monitor_support_diagnostic_caption),
        expanded = expanded, onExpandToggle = { onExpanded(!expanded) },
    ) {
        MonitorSettingsInlineRow(
            stringResource(R.string.monitor_support_save_report),
            stringResource(R.string.monitor_support_save_report_help),
            showTopDivider = false,
        ) { MonitorSettingsActionPill(stringResource(R.string.monitor_support_save), onClick = save) }
        MonitorSettingsInlineRow(stringResource(R.string.monitor_support_delete_reports), deleteHelp) {
            MonitorSettingsActionPill(stringResource(R.string.monitor_support_delete), onClick = delete)
        }
    }
}
