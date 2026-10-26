package com.opencapture.opencine.appkit

import androidx.annotation.StringRes

/**
 * Operator copy for the Display settings. The enum tokens ([MonitorDisplaySection.title],
 * [MonitorDisplayMode.settingsTitle] and [MonitorDisplayMode.settingsCaption]) stay the
 * untranslated identity the app, its stores and its tests read; only the drawn word resolves
 * through these resources, so a translated label can never reach persisted state or a wire value.
 */
@get:StringRes
internal val MonitorDisplayMode.settingsTitleRes: Int
    get() =
        when (this) {
            MonitorDisplayMode.LIVE -> R.string.display_live_title
            MonitorDisplayMode.CLEAN -> R.string.display_clean_title
            MonitorDisplayMode.COMMAND -> R.string.display_command_title
        }

@get:StringRes
internal val MonitorDisplayMode.settingsCaptionRes: Int
    get() =
        when (this) {
            MonitorDisplayMode.LIVE -> R.string.display_live_caption
            MonitorDisplayMode.CLEAN -> R.string.display_clean_caption
            MonitorDisplayMode.COMMAND -> R.string.display_command_caption
        }

@get:StringRes
internal val MonitorDisplaySection.titleRes: Int
    get() =
        when (this) {
            MonitorDisplaySection.STATUS_BAR -> R.string.display_section_status_bar
            MonitorDisplaySection.TOOL_BAR -> R.string.display_section_tool_bar
            MonitorDisplaySection.CAMERA_VALUES -> R.string.display_section_camera_values
            MonitorDisplaySection.LOCK_BUTTON -> R.string.display_section_lock_button
            MonitorDisplaySection.BATTERIES -> R.string.display_section_batteries
            MonitorDisplaySection.RAIL_RECORD -> R.string.display_section_record
            MonitorDisplaySection.RAIL_MEDIA -> R.string.display_section_media
            MonitorDisplaySection.RAIL_SETTINGS -> R.string.display_section_settings
            MonitorDisplaySection.ZOOM_CHIP -> R.string.display_section_zoom_chip
            MonitorDisplaySection.GIMBAL_STICK -> R.string.display_section_gimbal_stick
            MonitorDisplaySection.FOCUS_BOX -> R.string.display_section_af_box
            MonitorDisplaySection.REC_READOUT -> R.string.display_section_rec
            MonitorDisplaySection.TIMECODE -> R.string.display_section_timecode
            MonitorDisplaySection.FORMAT -> R.string.display_section_format
            MonitorDisplaySection.CODEC -> R.string.display_section_codec
            MonitorDisplaySection.COLOR -> R.string.display_section_color
            MonitorDisplaySection.STORAGE -> R.string.display_section_storage
            MonitorDisplaySection.FPS -> R.string.display_section_fps
        }

/** The one-line help under each switch, matching [displayHelp]'s English copy. */
@get:StringRes
internal val MonitorDisplaySection.helpRes: Int
    get() =
        when (this) {
            MonitorDisplaySection.STATUS_BAR -> R.string.display_help_status_bar
            MonitorDisplaySection.TOOL_BAR -> R.string.display_help_tool_bar
            MonitorDisplaySection.CAMERA_VALUES -> R.string.display_help_camera_values
            MonitorDisplaySection.LOCK_BUTTON -> R.string.display_help_lock_button
            MonitorDisplaySection.BATTERIES -> R.string.display_help_batteries
            MonitorDisplaySection.REC_READOUT -> R.string.display_help_rec
            MonitorDisplaySection.TIMECODE -> R.string.display_help_timecode
            MonitorDisplaySection.FORMAT -> R.string.display_help_format
            MonitorDisplaySection.CODEC -> R.string.display_help_codec
            MonitorDisplaySection.COLOR -> R.string.display_help_color
            MonitorDisplaySection.STORAGE -> R.string.display_help_storage
            MonitorDisplaySection.FPS -> R.string.display_help_fps
            MonitorDisplaySection.RAIL_RECORD -> R.string.display_help_rail_record
            MonitorDisplaySection.RAIL_MEDIA -> R.string.display_help_rail_media
            MonitorDisplaySection.RAIL_SETTINGS -> R.string.display_help_rail_settings
            MonitorDisplaySection.ZOOM_CHIP -> R.string.display_help_zoom_chip
            MonitorDisplaySection.GIMBAL_STICK -> R.string.display_help_gimbal_stick
            MonitorDisplaySection.FOCUS_BOX -> R.string.display_help_af_box
        }
