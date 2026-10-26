package com.opencapture.opencine.appkit

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.opencapture.monitorui.MonitorAssistTool
import com.opencapture.monitorui.settings.MonitorDisplaySectionStyle
import com.opencapture.monitorui.settings.MonitorDisplaySections
import com.opencapture.monitorui.settings.MonitorDisplayToggle
import com.opencapture.monitorui.settings.MonitorDisplaySection as DisplaySection

/** Common display composition over existing, app-namespaced preference values, one section per
 * DISP mode the source offers. */
@Composable
public fun MonitorOperatorDisplaySections(
    modes: List<MonitorDisplayMode>,
    chrome: (MonitorDisplayMode) -> MonitorDisplayChrome,
    assist: MonitorAssistState,
    onChange: (MonitorDisplayMode, MonitorDisplayChrome) -> Unit,
    available: Set<MonitorDisplaySection> = MonitorDisplaySection.entries.toSet(),
    pinnable: Set<MonitorAssistTool> = MonitorAssistTool.cleanPinCases.toSet(),
    onPinsChanged: ((Set<MonitorAssistTool>) -> Unit)? = null,
    edit: ((MonitorDisplayMode) -> Unit)? = null,
    feedback: () -> Unit = {},
): Unit {
    val order = listOf(MonitorDisplaySection.STATUS_BAR, MonitorDisplaySection.TOOL_BAR,
        MonitorDisplaySection.CAMERA_VALUES, MonitorDisplaySection.LOCK_BUTTON, MonitorDisplaySection.BATTERIES,
        MonitorDisplaySection.REC_READOUT, MonitorDisplaySection.TIMECODE, MonitorDisplaySection.FORMAT,
        MonitorDisplaySection.CODEC, MonitorDisplaySection.COLOR, MonitorDisplaySection.STORAGE, MonitorDisplaySection.FPS,
        MonitorDisplaySection.RAIL_RECORD, MonitorDisplaySection.RAIL_MEDIA, MonitorDisplaySection.RAIL_SETTINGS,
        MonitorDisplaySection.ZOOM_CHIP, MonitorDisplaySection.GIMBAL_STICK, MonitorDisplaySection.FOCUS_BOX)
    val sections = mutableListOf<DisplaySection>()
    for (mode in modes) {
        val value = chrome(mode)
        sections += DisplaySection(id = mode.name, title = stringResource(mode.settingsTitleRes),
            caption = stringResource(mode.settingsCaptionRes),
            reset = {
                feedback()
                onChange(mode, MonitorDisplayChrome.defaults(mode))
            }, editTitle = stringResource(R.string.display_edit_view),
            editHint = stringResource(R.string.display_edit_hint),
            edit = edit?.let { action -> { action(mode) } },
            unavailableEditHelp = stringResource(R.string.display_edit_unavailable),
            visibility = visibilityToggles(order, available, mode, value, feedback, onChange),
            pinsTitle = stringResource(R.string.display_pins_title),
            pins = pinToggles(mode, pinnable, assist, onPinsChanged, feedback),
            style = MonitorDisplaySectionStyle.CONTROLS)
    }
    MonitorDisplaySections(sections)
}

/**
 * The mode's switch rows. The resolved words are read one row at a time because a translated
 * label is drawn, never stored: the row's identity stays `mode.name + section.name`.
 */
@Composable
private fun visibilityToggles(
    order: List<MonitorDisplaySection>,
    available: Set<MonitorDisplaySection>,
    mode: MonitorDisplayMode,
    value: MonitorDisplayChrome,
    feedback: () -> Unit,
    onChange: (MonitorDisplayMode, MonitorDisplayChrome) -> Unit,
): List<MonitorDisplayToggle> {
    val toggles = mutableListOf<MonitorDisplayToggle>()
    for (section in order) {
        if (section !in available || !MonitorDisplayChrome.isConfigurable(section, mode)) continue
        val title = stringResource(section.titleRes)
        toggles += MonitorDisplayToggle(id = mode.name + "." + section.name, title = title,
            isOn = value.isVisible(section), help = stringResource(section.helpRes)) {
            feedback(); onChange(mode, value.toggling(section))
        }
    }
    return toggles
}

/** Clean view's pinned assists, named with the same words the palette chips use. */
@Composable
private fun pinToggles(
    mode: MonitorDisplayMode,
    pinnable: Set<MonitorAssistTool>,
    assist: MonitorAssistState,
    onPinsChanged: ((Set<MonitorAssistTool>) -> Unit)?,
    feedback: () -> Unit,
): List<MonitorDisplayToggle> {
    if (mode != MonitorDisplayMode.CLEAN) return emptyList()
    val toggles = mutableListOf<MonitorDisplayToggle>()
    for (tool in MonitorAssistTool.cleanPinCases) {
        if (tool !in pinnable) continue
        val title = tool.titleRes?.let { stringResource(it) } ?: tool.title
        toggles += MonitorDisplayToggle(id = "clean.pin." + tool.name, title = title,
            isOn = tool in assist.pinned, accessibilityLabel = stringResource(R.string.display_pin_a11y, title)) {
            feedback()
            if (onPinsChanged == null) assist.togglePin(tool)
            else {
                val next = if (tool in assist.pinned) assist.pinned - tool else assist.pinned + tool
                assist.pinned = next; onPinsChanged(next)
            }
        }
    }
    return toggles
}
