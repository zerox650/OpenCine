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
            R.string.capture_angle_actual to "Angle · actual %1\$s",
            R.string.delivery_upload_unavailable to "Upload is unavailable.",
            R.string.delivery_upload_service to "Upload service",
            R.string.delivery_clips_will_cache to "%1\$d on-camera clip(s) will be cached from the camera first.",
            R.string.delivery_clips_will_skip to "%1\$d on-camera clip(s) will be skipped; reconnect to cache them.",
            R.string.delivery_not_configured to "%1\$s isn't configured; add credentials in Settings → Storage.",
            R.string.delivery_project to "Project: %1\$s",
            R.string.delivery_gate_with_project to
                "%1\$s needs the internet. Hop off the camera's Wi-Fi to pick a project (currently “%2\$s”) and upload; the camera reconnects automatically when you're done.",
            R.string.delivery_gate to
                "%1\$s needs the internet. Hop off the camera's Wi-Fi to sign in and pick a project; the camera reconnects automatically when you're done.",
            R.string.delivery_projects_header to "PROJECT",
            R.string.delivery_projects_help to "Upload destination in your %1\$s workspace.",
            R.string.delivery_sign_in to "Sign in to %1\$s from Settings → Storage to upload.",
            R.string.delivery_convert_log to "Convert log",
            R.string.delivery_output_curve to "Output curve",
            R.string.delivery_bake_lut to "Bake LUT",
            R.string.delivery_apply_lut to "Apply %1\$s to exports.",
            R.string.delivery_apply_selected_lut to "Apply the selected LUT to exports.",
            R.string.delivery_no_lut to "No LUT selected. Pick one in view assists.",
            R.string.delivery_bake_exposure to "Bake exposure",
            R.string.delivery_bake_exposure_help to
                "Write the LUT exposure pull into the file so it matches the monitor. Off bakes the cube at 0.0.",
            R.string.delivery_format to "Format",
            R.string.delivery_format_help to "MOV preserves quality; MP4 is more widely compatible.",
            R.string.delivery_include_metadata to "Include metadata",
            R.string.delivery_include_metadata_help to "Filename, capture date, and size (best-effort JSON sidecar).",
            R.string.delivery_reupload to "Re-upload already uploaded",
            R.string.delivery_reupload_help to "Skip clips already on %1\$s unless enabled.",
            R.string.delivery_share to "Share",
            R.string.delivery_clip to "%1\$d clip",
            R.string.delivery_clips to "%1\$d clips",
            R.string.delivery_dest_share_help to "Nearby Share, Files, and other apps",
            R.string.delivery_save_to_photos to "Save to Photos",
            R.string.delivery_upload to "Upload",
            R.string.delivery_size_unavailable to "Size unavailable",
            R.string.delivery_at_least to "At least %1\$s",
            R.string.delivery_converted_log to "Converted log",
            R.string.delivery_lut_baked to "LUT baked",
            R.string.delivery_camera_original to "Camera original",
            R.string.delivery_summary to "%1\$s · %2\$s · %3\$s",
        )
}

/** A resource in the operator's language, or its verbatim English when there is no context. */
internal fun lstr(context: Context?, resId: Int, vararg args: Any): String {
    val template = context?.getString(resId) ?: Strings.english[resId] ?: return ""
    return if (args.isEmpty()) template else String.format(template, *args)
}
