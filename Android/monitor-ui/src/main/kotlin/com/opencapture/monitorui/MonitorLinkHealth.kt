package com.opencapture.monitorui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

/** Dash-scale bands shared by Operator Setup Link Health and the live signal gauge. */
object MonitorLinkHealth {
    val stable = Color(0.18f, 0.78f, 0.42f)
    val watch = Color(0.96f, 0.52f, 0.12f)
    val poor = MonitorPalette.recording

    /** [label] stays the untranslated band name; operator copy reads [labelRes]. */
    enum class Band(val label: String, @StringRes val labelRes: Int) {
        POOR("Poor", R.string.monitor_link_poor),
        WATCH("Watch", R.string.monitor_link_watch),
        STABLE("Stable", R.string.monitor_link_stable),
    }

    fun score(bars: Int): Int = bars.coerceIn(0, 4) * 25

    fun band(score: Int): Band = when {
        score >= 80 -> Band.STABLE
        score >= 50 -> Band.WATCH
        else -> Band.POOR
    }

    fun color(score: Int): Color = when (band(score)) {
        Band.STABLE -> stable
        Band.WATCH -> watch
        Band.POOR -> poor
    }
}
