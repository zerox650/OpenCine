package com.opencapture.opencine.cameracontrols

import android.content.Context
import com.opencapture.opencine.cameracontrols.CameraControlsStrings as Strings

/**
 * Verbatim English for the copy this module reads off a Context. Rules and tests build without
 * one, and they assert the same words the operator reads in English, so every resource a plain
 * function resolves has its English here.
 */
public object CameraControlsStrings {
    internal val english: Map<Int, String> =
        mapOf(
            R.string.media_selection_delete to "Delete selected media",
            R.string.media_selection_delete_one to "Delete %1\$d selected item from the camera card",
            R.string.media_selection_delete_many to "Delete %1\$d selected items from the camera card",
            R.string.media_selection_share_one to "Share %1\$d selected item",
            R.string.media_selection_share_many to "Share %1\$d selected items",
        )
}

/** A resource in the operator's language, or its verbatim English when there is no context. */
internal fun lstr(context: Context?, resId: Int, vararg args: Any): String {
    val template = context?.getString(resId) ?: Strings.english[resId] ?: return ""
    return if (args.isEmpty()) template else String.format(template, *args)
}
