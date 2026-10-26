package com.opencapture.monitorui

import androidx.annotation.StringRes

/** Current monitor-assist identities. Sources provide availability and choose their tool ordering. */
enum class MonitorAssistTool {
    LUT,
    PEAK,
    FALSE,
    ZEBRA,
    WAVE,
    PARADE,
    HISTO,
    VECTOR,
    LIGHTS,
    ND,
    EV,
    AUDIO,
    GUIDES,
    GRID,
    CROSS,
    LEVEL,
    DESQ,
    MIRROR,
    ;

    val chipLabel: String
        get() = if (this == DESQ) "DE-SQ" else name

    val label: String
        get() = name

    /**
     * The palette and inspector words for this tool, or null when the English chip stays
     * (LUT, ND, EV and DE-SQ are abbreviations every operator reads). [chipLabel] remains the
     * identity: it round-trips through [fromPersisted] and the palette's usage keys, so only
     * the drawn word resolves.
     */
    @get:StringRes
    val chipLabelRes: Int?
        get() =
            when (this) {
                PEAK -> R.string.monitor_assist_chip_peak
                FALSE -> R.string.monitor_assist_chip_false
                ZEBRA -> R.string.monitor_assist_chip_zebra
                WAVE -> R.string.monitor_assist_chip_wave
                PARADE -> R.string.monitor_assist_chip_parade
                HISTO -> R.string.monitor_assist_chip_histo
                VECTOR -> R.string.monitor_assist_chip_vector
                LIGHTS -> R.string.monitor_assist_chip_lights
                AUDIO -> R.string.monitor_assist_chip_audio
                GUIDES -> R.string.monitor_assist_chip_guides
                GRID -> R.string.monitor_assist_chip_grid
                CROSS -> R.string.monitor_assist_chip_cross
                LEVEL -> R.string.monitor_assist_chip_level
                MIRROR -> R.string.monitor_assist_chip_mirror
                LUT, ND, EV, DESQ -> null
            }

    /** Settings names, matching the shared Swift catalog. */
    val displaySettingsTitle: String
        get() = when (this) {
            PARADE -> "Parade"
            ND -> "ND"
            else -> title
        }

    /** Operator copy for the settings card; [title] stays the untranslated token. */
    @get:StringRes
    val titleRes: Int?
        get() =
            when (this) {
                LUT -> null
                PEAK -> R.string.monitor_assist_peaking
                FALSE -> R.string.monitor_assist_false_color
                ZEBRA -> R.string.monitor_assist_zebra
                WAVE -> R.string.monitor_assist_waveform
                PARADE -> R.string.monitor_assist_parade
                HISTO -> R.string.monitor_assist_histogram
                VECTOR -> R.string.monitor_assist_vectorscope
                LIGHTS -> R.string.monitor_assist_traffic_lights
                ND -> R.string.monitor_assist_nd
                EV -> R.string.monitor_assist_ev_meter
                AUDIO -> R.string.monitor_assist_audio
                GUIDES -> R.string.monitor_assist_guides
                GRID -> R.string.monitor_assist_grid
                CROSS -> R.string.monitor_assist_crosshair
                LEVEL -> R.string.monitor_assist_level
                DESQ -> R.string.monitor_assist_desqueeze
                MIRROR -> R.string.monitor_assist_mirror
            }

    val title: String
        get() =
            when (this) {
                LUT -> "LUT"
                PEAK -> "Peaking"
                FALSE -> "False Color"
                ZEBRA -> "Zebra"
                WAVE -> "Waveform"
                PARADE -> "RGB Parade"
                HISTO -> "Histogram"
                VECTOR -> "Vectorscope"
                LIGHTS -> "Traffic Lights"
                ND -> "ND Suggestion"
                EV -> "EV Meter"
                AUDIO -> "Audio Levels"
                GUIDES -> "Guides"
                GRID -> "Grid"
                CROSS -> "Crosshair"
                LEVEL -> "Level"
                DESQ -> "Anamorphic Desqueeze"
                MIRROR -> "Mirror"
            }

    /** The fixed camera EV meter and LEVEL are tap-only toggles. */
    val hasConfiguration: Boolean
        get() =
            when (this) {
                EV, LEVEL -> false
                else -> true
            }

    companion object {
        val toolbarGroups: List<List<MonitorAssistTool>> get() =
            listOf(
                listOf(LUT, PEAK, FALSE),
                listOf(ZEBRA, WAVE, PARADE),
                listOf(HISTO, VECTOR, LIGHTS, ND, EV),
                listOf(GUIDES, GRID, CROSS, LEVEL, DESQ),
                listOf(MIRROR),
            )

        /** AUDIO is appended as its own trailing section. */
        val toolbarCases: List<MonitorAssistTool> get() = toolbarGroups.flatten()

        /** Camera EV and LEVEL have no clip telemetry; AUDIO rides last like live. */
        val playbackToolbarCases: List<MonitorAssistTool> get() = toolbarCases.filter { it != EV && it != LEVEL } + AUDIO

        val settingsCases: List<MonitorAssistTool> get() = toolbarCases + AUDIO

        val cleanPinCases: List<MonitorAssistTool> get() = settingsCases.filter { it != EV }

        fun fromPersisted(raw: String): MonitorAssistTool? =
            entries.firstOrNull { it.name == raw || it.chipLabel == raw }
    }
}
