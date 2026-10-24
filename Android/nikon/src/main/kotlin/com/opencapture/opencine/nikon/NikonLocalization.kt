package com.opencapture.opencine.nikon

import android.content.Context
import java.util.Locale

/**
 * Resolves a Nikon connection-flow string resource, or — when no Android [Context] is available
 * (e.g. the JVM unit tests) — falls back to the bundled English copy so behaviour and assertions
 * are unchanged off-device. Pass a [Context] from a Composable (`LocalContext.current`) to
 * localize the presentation.
 *
 * The English copy is byte-for-byte the literals in [NikonIntegration] (curly apostrophe
 * U+2019, non-breaking hyphen U+2011, curly quotes U+201C/U+201D), because `NikonIntegrationTest`
 * asserts those step titles verbatim.
 */
private val NIKON_ENGLISH_STRINGS = mapOf(
    R.string.nikon_step_joining_wifi to "Joining the camera\u2019s Wi-Fi",
    R.string.nikon_step_finding_camera to "Finding the camera",
    R.string.nikon_step_connecting to "Connecting",
    R.string.nikon_step_pairing to "Pairing",
    R.string.nikon_step_starting_live to "Starting live view",
    R.string.nikon_step_waiting_on to "Waiting for the camera on %1\$s",

    R.string.nikon_route_its_wifi to "its Wi-Fi",
    R.string.nikon_route_wifi to "Wi-Fi",
    R.string.nikon_route_your_hotspot to "your hotspot",
    R.string.nikon_route_usb_c to "USB-C",
    R.string.nikon_route_hdmi to "HDMI",

    R.string.nikon_detail_ready_join to "Join %1\$s's Wi\u2011Fi network to continue.",
    R.string.nikon_detail_joining_wifi to "Approve the network request to switch networks.",
    R.string.nikon_detail_discovering to "Looking for %1\$s on your network.",
    R.string.nikon_detail_handshaking to "Establishing a secure link with %1\$s.",
    R.string.nikon_detail_pairing to "Confirm the pairing request on %1\$s.",
    R.string.nikon_detail_confirm_on_camera to "Tap \u201cConfirm\u201d on %1\$s. It will restart its Wi\u2011Fi, then this phone reconnects.",
    R.string.nikon_detail_preparing_live to "Opening the live monitor for %1\$s.",

    R.string.nikon_fail_route_unreachable to "This setup can no longer be reached.",
    R.string.nikon_fail_route_cannot_add to "This setup cannot be added.",
    R.string.nikon_fail_not_connected to "The camera did not connect.",

    R.string.nikon_takeover_title to "Take over this camera?",
    R.string.nikon_takeover_confirm to "Take Over",
    R.string.nikon_takeover_cancel to "Cancel",
    R.string.nikon_takeover_message_holder to "%1\$s is using this camera. Connecting will disconnect it.",
    R.string.nikon_takeover_message_other to "Another device is using this camera. Connecting will disconnect it.",
)

internal fun lstr(context: Context?, resId: Int, vararg args: Any): String {
    if (context != null) return context.getString(resId, *args)
    val template = NIKON_ENGLISH_STRINGS[resId] ?: return ""
    return if (args.isEmpty()) template else String.format(Locale.US, template, *args)
}
