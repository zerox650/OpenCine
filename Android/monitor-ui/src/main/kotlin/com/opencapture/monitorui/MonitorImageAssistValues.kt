package com.opencapture.monitorui

import androidx.annotation.StringRes

/**
 * [persisted] is the saved key. [STOPS] is the scale now labeled Video; its key stays
 * "CineStop" so saved settings keep the scale they chose. CineStop is [SCENE_STOPS].
 * [menuLabel] is the round-trip word ([fromMenuLabel]); [menuLabelRes] is what an operator
 * reads, and stays null for the words that are already technical or product names.
 */
enum class MonitorFalseColorSelection(val persisted: String, val menuLabel: String) {
    SCENE_STOPS("SceneStops", "CineStop"),
    STOPS("CineStop", "Video"),
    IRE("IRE", "IRE"),
    LIMITS("Limits", "Limits"),
    ;

    val usesSceneStops: Boolean get() = this == SCENE_STOPS

    @get:StringRes
    val menuLabelRes: Int?
        get() =
            when (this) {
                STOPS -> R.string.monitor_false_color_scale_video
                LIMITS -> R.string.monitor_false_color_scale_limits
                // CineStop and IRE are the product's own scale names; they stay as written.
                SCENE_STOPS, IRE -> null
            }

    companion object {
        fun fromPersisted(raw: String): MonitorFalseColorSelection =
            entries.firstOrNull { it.persisted == raw || it.name == raw }
                ?: when (raw) {
                    "Stops", "ZC Stops", "PStops" -> STOPS
                    else -> SCENE_STOPS
                }

        fun fromMenuLabel(label: String): MonitorFalseColorSelection =
            when (label) {
                "Video" -> STOPS
                "IRE" -> IRE
                "Limits" -> LIMITS
                else -> SCENE_STOPS
            }
    }
}

enum class MonitorZebraUnit(val persisted: String, val editorLabel: String) {
    NATIVE("Native", "0-255"),
    IRE("IRE", "IRE"),
    ;

    companion object {
        fun fromPersisted(raw: String): MonitorZebraUnit =
            entries.firstOrNull { it.persisted == raw || it.editorLabel == raw || it.name == raw }
                ?: IRE

        fun fromEditorLabel(label: String): MonitorZebraUnit = if (label == "0-255") NATIVE else IRE
    }
}
