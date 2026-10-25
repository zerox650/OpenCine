package com.opencapture.monitorui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.opencapture.monitorui.MonitorIcon
import com.opencapture.monitorui.MonitorPalette
import com.opencapture.monitorui.MonitorTypography
import com.opencapture.monitorui.R

/** One language the operator can pick. A null [tag] follows the device's own language. */
public data class MonitorSettingsLanguageChoice(
    public val tag: String?,
    public val title: String,
)

/**
 * The app language row. [selection] is the stored language tag, or null/blank to follow the
 * device. The host owns both the list and what it does with a choice: the shared settings only
 * draw the menu.
 */
public class MonitorSettingsLanguageSource(
    public val selection: String?,
    public val choices: List<MonitorSettingsLanguageChoice>,
    public val select: (String?) -> Unit,
)

/** The chosen language, falling back to the first when the stored tag is not on offer. */
private fun MonitorSettingsLanguageSource.selected(): MonitorSettingsLanguageChoice? =
    choices.firstOrNull { it.tag == selection } ?: choices.firstOrNull()

/**
 * Language, on the System tab: the current choice with a menu of the languages the app ships.
 * A dropdown, not a segmented row: the list is a preference the operator sets once, the labels
 * are always written in their own language, and their count is the app's, not the camera's.
 */
@Composable
public fun MonitorLanguageSettings(source: MonitorSettingsLanguageSource) {
    var expanded by remember { mutableStateOf(false) }
    val title = stringResource(R.string.monitor_language_title)
    val rowName = stringResource(R.string.monitor_language_a11y)
    val selected = source.selected()
    MonitorSettingsRowCard(title = title) {
        Box {
            MonitorSettingsInlineRow(
                stringResource(R.string.monitor_language_picker),
                stringResource(R.string.monitor_language_help),
                showTopDivider = false,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MonitorSettingsValueText(selected?.title.orEmpty())
                    MonitorIcon(MonitorIcon.CHEVRON_DOWN, null, Modifier.size(12.dp), MonitorPalette.muted)
                }
            }
            // The menu opens from the row: the whole line is one target, and TalkBack reads the
            // row as a named button rather than two nodes (a value plus an icon).
            Box(
                Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, indication = null,
                        role = Role.Button, onClick = { expanded = true },
                    )
                    .testTag(MONITOR_LANGUAGE_ROW_TAG)
                    .semantics { contentDescription = rowName },
            )
            MonitorLanguageMenu(expanded, source) { expanded = false }
        }
    }
}

@Composable
private fun MonitorLanguageMenu(
    expanded: Boolean,
    source: MonitorSettingsLanguageSource,
    onDismiss: () -> Unit,
) {
    val selected = source.selected()
    DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
        source.choices.forEach { choice ->
            val chosen = choice.tag == selected?.tag
            DropdownMenuItem(
                text = {
                    Text(
                        choice.title,
                        style = MonitorTypography.text(12.5f, if (chosen) FontWeight.SemiBold else FontWeight.Medium),
                        color = if (chosen) MonitorPalette.accent else MonitorPalette.text,
                    )
                },
                onClick = { onDismiss(); if (!chosen) source.select(choice.tag) },
            )
        }
    }
}

/** The language row's UI test tag; the tag is stable, the labels are not. */
public const val MONITOR_LANGUAGE_ROW_TAG: String = "monitor.settings.language"
