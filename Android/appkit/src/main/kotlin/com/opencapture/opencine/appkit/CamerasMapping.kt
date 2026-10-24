package com.opencapture.opencine.appkit

import com.opencapture.monitorui.MonitorConnectFailure
import com.opencapture.monitorui.MonitorConnectStep
import com.opencapture.monitorui.MonitorPairingDevice
import com.opencapture.monitorui.MonitorPairingInstruction
import com.opencapture.monitorui.MonitorPairingInstructionIcon
import com.opencapture.monitorui.MonitorPairingPresentation
import com.opencapture.monitorui.MonitorPairingStep
import com.opencapture.monitorui.MonitorSetupChip
import com.opencapture.opencine.core.Availability
import com.opencapture.opencine.core.ConnectTarget
import com.opencapture.opencine.core.ConnectionFailure
import com.opencapture.opencine.core.ConnectionPrompt
import com.opencapture.opencine.core.ConnectionRouteID
import com.opencapture.opencine.core.RecoveryAction
import com.opencapture.opencine.core.RouteKind
import com.opencapture.opencine.core.SavedCamera
import com.opencapture.opencine.core.SavedRoute

import android.content.Context
import java.util.Locale

/**
 * Resolves a string resource, or — when no Android [Context] is available (e.g. JVM unit tests) —
 * falls back to the bundled English copy so behaviour and assertions are unchanged off-device.
 * Pass a [Context] from a Composable (`LocalContext.current`) to localize the presentation.
 */
internal val ENGLISH_STRINGS = mapOf(
    R.string.appkit_on_the_camera to "On the camera",
    R.string.appkit_on_this_phone to "On this phone",
    R.string.appkit_next to "Next",
    R.string.appkit_find_your_camera to "Find your camera",
    R.string.appkit_nearby_cameras to "Nearby cameras",
    R.string.appkit_turn_camera_on to "Turn the camera on and keep this phone nearby.",
    R.string.appkit_nothing_selected_yet to "Nothing selected yet",
    R.string.appkit_selected_camera to "Selected %1\$s",
    R.string.appkit_choose_camera to "Choose the camera that matches your screen",
    R.string.appkit_monitoring_opens to "Monitoring opens when the picture is ready",
    R.string.appkit_connecting to "Connecting…",
    R.string.appkit_camera_model_nearby to "%1\$s · nearby",
    R.string.appkit_looking_for_cameras to "Looking for cameras",
    R.string.appkit_no_cameras_yet to "No cameras yet",
    R.string.appkit_open_settings to "Open Settings",
    R.string.appkit_try_again to "Try again",
    R.string.appkit_continue to "Continue",
    R.string.appkit_cancel to "Cancel",
    R.string.appkit_back to "Back",

    // Camera list screen (paired / nearby cards)
    R.string.appkit_badge_connecting to "CONNECTING",
    R.string.appkit_badge_not_connected to "NOT CONNECTED",
    R.string.appkit_badge_last_used to "LAST USED",
    R.string.appkit_badge_paired to "PAIRED",
    R.string.appkit_badge_offline to "OFFLINE",
    R.string.appkit_badge_in_use to "IN USE",
    R.string.appkit_badge_new to "NEW",
    R.string.appkit_status_nearby_ready to "Nearby · ready to connect",
    R.string.appkit_status_not_found to "Not found. Power it on to reconnect",
    R.string.appkit_status_in_use to "In use by another device",
    R.string.appkit_status_ready_to_pair to "Ready to pair",
    R.string.appkit_action_connect to "Connect",
    R.string.appkit_action_reconnect to "Reconnect",
    R.string.appkit_action_pair to "Pair",
    R.string.appkit_action_edit_setup to "Edit setup",
    R.string.appkit_action_pair_again to "Pair again",
    R.string.appkit_route_camera_wifi to "Camera Wi-Fi",
    R.string.appkit_route_wifi to "Wi-Fi",
    R.string.appkit_route_wifi_network to "Wi-Fi · %1\$s",
    R.string.appkit_route_hotspot to "Hotspot",
    R.string.appkit_route_usb_c to "USB-C",
    R.string.appkit_route_hdmi to "HDMI",
    R.string.appkit_nearby_generic to "nearby",
    R.string.appkit_connect_over to "Connect over %1\$s instead",
    R.string.appkit_another_setup to "another setup",

    // Pairing wizard (legacy flow; extracted for completeness) — kept verbatim for JVM tests.
    R.string.appkit_wizard_title_find to "Find your camera",
    R.string.appkit_wizard_title_approve to "Approve on the camera",
    R.string.appkit_wizard_title_join to "Join camera Wi-Fi",
    R.string.appkit_wizard_title_video to "Open video link",
    R.string.appkit_wizard_rail_bluetooth to "Bluetooth scan",
    R.string.appkit_wizard_rail_prompt to "Camera prompt",
    R.string.appkit_wizard_rail_network to "Camera network",
    R.string.appkit_wizard_rail_video to "Video link",
    R.string.appkit_wizard_wait_bt to "Wait here, we keep the Bluetooth link alive",
    R.string.appkit_wizard_no_quit to "Don't force-quit the app",
    R.string.appkit_wizard_tap_join to "Tap Join when Android asks to join the camera network",
    R.string.appkit_wizard_check_connection to "Check the connection",
    R.string.appkit_wizard_bt_link to "Bluetooth link",
    R.string.appkit_wizard_camera_connected to "Camera connected",
    R.string.appkit_wizard_ok to "OK",
    R.string.appkit_wizard_waiting_network to "Waiting for the camera network",
    R.string.appkit_wizard_joining to "JOINING",
    R.string.appkit_wizard_live_picture to "Live picture",
    R.string.appkit_wizard_opening_video to "Opening the video link…",
    R.string.appkit_wizard_starts_after to "Starts after Wi-Fi joins",
    R.string.appkit_wizard_waiting to "WAITING",
    R.string.appkit_wizard_nothing_type to "Nothing to type. Approve it on the camera",
    R.string.appkit_wizard_android_asks to "Android asks to join the camera network",

    // Monitor control notes (MonitorControlModel) — kept verbatim for JVM tests.
    R.string.monitor_connect_first to "Connect the camera to use its controls.",
    R.string.monitor_recording_unknown to "Recording state is not known yet. Try again in a moment.",
    R.string.monitor_note_refused to "%1\$s: the camera refused it.",
    R.string.monitor_note_not_offered to "%1\$s is not offered by this camera.",
    R.string.monitor_note_stale to "%1\$s was not sent: the camera state changed. Try again.",
    R.string.monitor_note_unavailable_mode to "%1\$s is not available in this mode.",
    R.string.monitor_note_unavailable_now to "%1\$s is not available right now.",
    R.string.monitor_note_not_applied to "%1\$s was not applied by the camera.",
    R.string.monitor_note_not_sent to "%1\$s was not sent.",
    R.string.monitor_note_link_dropped to "%1\$s: the camera link dropped before it confirmed.",
    R.string.monitor_note_not_confirmed to "The camera did not confirm %1\$s.",
    R.string.monitor_label_record to "Record",
    R.string.monitor_label_stop to "Stop",
    R.string.appkit_confirm_start_recording to "Start recording?",
    R.string.appkit_confirm_stop_recording to "Stop recording?",
    R.string.appkit_action_start to "Start",
    R.string.appkit_action_stop to "Stop",
    R.string.appkit_chrome_edit_title to "Editing %1\$s",
    R.string.appkit_chrome_edit_hint to "Tap an eye to show or hide it",
)

internal fun lstr(context: Context?, resId: Int, vararg args: Any): String {
    if (context != null) return context.getString(resId, *args)
    val template = ENGLISH_STRINGS[resId] ?: return ""
    return if (args.isEmpty()) template else String.format(Locale.US, template, *args)
}

/** One card of the cameras page (Swift `CameraListItem`). */
public data class CameraItem(
    public val id: String,
    public val name: String,
    public val subtitle: String,
    public val badge: String = "",
    public val status: String,
    public val actionTitle: String,
    public val isPrimary: Boolean = false,
    public val isBusy: Boolean = false,
    public val isAvailable: Boolean = true,
    public val signalBars: Int? = null,
    public val setups: List<MonitorSetupChip> = emptyList(),
    public val canAddSetup: Boolean = false,
    public val steps: List<MonitorConnectStep> = emptyList(),
    public val failure: MonitorConnectFailure? = null,
)

/** The route id as both platforms print it (Swift `UUID.uuidString`). */
public val ConnectionRouteID.text: String get() = uuid.toString().uppercase()

/**
 * The model as the shared camera pages show it (Swift `CamerasMapping`, same values). Integration
 * copy (steps, prompts, failures) is rendered as given; the shell's own copy is the pages' frame.
 */
public object CamerasMapping {
    /** Reopens a failed saved route only when it still exists and the chooser advertises it. */
    public fun addSetupSelection(model: CamerasModel, camera: SavedCamera, chooser: AddSetupChooser): String? {
        val target = model.state.value.failures[camera.id]?.target as? ConnectTarget.Saved ?: return null
        val kind = camera.routes.firstOrNull { it.id == target.route }?.kind ?: return null
        return chooser.options.firstOrNull { it.kind == kind && it.enabled }?.id
    }

    /** Saved cameras, most recently connected first. */
    public fun paired(model: CamerasModel, context: Context? = null): List<CameraItem> {
        val state = model.state.value
        val ordered = state.saved.withIndex().sortedWith { a, b ->
            val x = a.value.lastConnected
            val y = b.value.lastConnected
            when {
                x != null && y != null && x != y -> y.compareTo(x)
                x != null && y == null -> -1
                x == null && y != null -> 1
                else -> a.index.compareTo(b.index)
            }
        }.map { it.value }
        val latest = ordered.firstOrNull { it.lastConnected != null }?.id
        return ordered.map { camera ->
            val attempt = state.attempts[camera.id]
            val failure = state.failures[camera.id]
            val nearby = sighting(camera, state) != null
            val addable = addableRoutes(camera, model)
            val active = attempt?.route ?: failure?.let { route(it.target) } ?: model.defaultRoute(camera)
            val network = camera.routes.firstOrNull { it.id == active }?.network
            CameraItem(
                id = camera.id,
                name = camera.displayName,
                subtitle = listOfNotNull(camera.model, network).filter { it.isNotEmpty() }.joinToString(" · "),
                badge = when {
                    attempt != null -> lstr(context, R.string.appkit_badge_connecting)
                    failure != null -> lstr(context, R.string.appkit_badge_not_connected)
                    camera.id == latest -> lstr(context, R.string.appkit_badge_last_used)
                    nearby -> lstr(context, R.string.appkit_badge_paired)
                    else -> lstr(context, R.string.appkit_badge_offline)
                },
                status = attempt?.let { status(it, context) } ?: if (nearby) lstr(context, R.string.appkit_status_nearby_ready) else lstr(context, R.string.appkit_status_not_found),
                actionTitle = if (nearby) lstr(context, R.string.appkit_action_connect) else lstr(context, R.string.appkit_action_reconnect),
                isPrimary = camera.id == latest,
                isBusy = attempt != null,
                isAvailable = nearby,
                setups = camera.routes.map {
                    MonitorSetupChip(
                        it.id.text, title(it, context), it.id == active, it.kind in addable && camera.routes.size > 1,
                        setupKind(it.kind),
                    )
                },
                // As on main: Add only while an addable kind is still missing.
                canAddSetup = (addable - camera.routes.map { it.kind }.toSet()).isNotEmpty(),
                steps = attempt?.let { steps(it) }.orEmpty(),
                failure = failure?.let { failure(it.failure, camera, context) },
            )
        }
    }

    /** Sightings that are not saved cameras. */
    public fun nearby(model: CamerasModel, context: Context? = null): List<CameraItem> {
        val state = model.state.value
        return state.nearby.filter { sighting -> state.saved.none { matches(sighting, it) } }.map { device(it, state, model, context) }
    }

    /** The chip title: the operator's setup name, else the shell's name for the kind. */
    public fun title(route: SavedRoute, context: Context? = null): String = route.title ?: when (route.kind) {
        RouteKind.CAMERA_ACCESS_POINT -> lstr(context, R.string.appkit_route_camera_wifi)
        RouteKind.INFRASTRUCTURE_NETWORK -> route.network?.let { lstr(context, R.string.appkit_route_wifi_network, it) } ?: lstr(context, R.string.appkit_route_wifi)
        RouteKind.PHONE_HOTSPOT -> lstr(context, R.string.appkit_route_hotspot)
        RouteKind.USB -> lstr(context, R.string.appkit_route_usb_c)
        RouteKind.CAPTURE_DEVICE -> lstr(context, R.string.appkit_route_hdmi)
    }

    /** The chip's accessibility name: the setup names the apps' tests have always used. */
    internal fun setupKind(kind: RouteKind): String = when (kind) {
        RouteKind.CAMERA_ACCESS_POINT -> "cameraWiFi"
        RouteKind.INFRASTRUCTURE_NETWORK -> "wifi"
        RouteKind.PHONE_HOTSPOT, RouteKind.USB, RouteKind.CAPTURE_DEVICE -> kind.rawValue
    }

    /** The attempt's plan as card steps; the running step carries the one live caption. */
    public fun steps(attempt: CamerasModel.Attempt): List<MonitorConnectStep> {
        val progress = attempt.progress ?: return emptyList()
        return progress.steps.mapIndexed { index, step ->
            val state = when {
                index < progress.current -> MonitorConnectStep.State.DONE
                index == progress.current -> MonitorConnectStep.State.ACTIVE
                else -> MonitorConnectStep.State.WAITING
            }
            val detail = if (index == progress.current) attempt.prompt?.text ?: progress.note ?: step.detail else ""
            MonitorConnectStep(step.title, detail, state)
        }
    }

    public fun failure(failure: ConnectionFailure, camera: SavedCamera? = null, context: Context? = null): MonitorConnectFailure {
        val actions = mutableListOf<MonitorConnectFailure.Action>()
        var link: MonitorConnectFailure.Action? = null
        for (action in failure.actions) {
            when (action) {
                is RecoveryAction.UseRoute -> {
                    val route = camera?.routes?.firstOrNull { it.id == action.route }
                    link = MonitorConnectFailure.Action(id(action), lstr(context, R.string.appkit_connect_over, route?.let { title(it, context) } ?: lstr(context, R.string.appkit_another_setup)))
                }
                RecoveryAction.Retry -> actions += MonitorConnectFailure.Action(id(action), lstr(context, R.string.appkit_try_again), primary = true)
                is RecoveryAction.EditRoute -> actions += MonitorConnectFailure.Action(id(action), lstr(context, R.string.appkit_action_edit_setup))
                RecoveryAction.PairAgain -> actions += MonitorConnectFailure.Action(id(action), lstr(context, R.string.appkit_action_pair_again))
                RecoveryAction.OpenSettings -> actions += MonitorConnectFailure.Action(id(action), lstr(context, R.string.appkit_open_settings))
                is RecoveryAction.Integration -> actions += MonitorConnectFailure.Action(id(action), action.title)
            }
        }
        return MonitorConnectFailure(failure.title, failure.message, actions, link)
    }

    /** The stable id of a failure action on the page. */
    public fun id(action: RecoveryAction): String = when (action) {
        RecoveryAction.Retry -> "retry"
        is RecoveryAction.UseRoute -> "route:${action.route.text}"
        is RecoveryAction.EditRoute -> "edit:${action.route.text}"
        RecoveryAction.PairAgain -> "pair"
        RecoveryAction.OpenSettings -> "settings"
        is RecoveryAction.Integration -> "integration:${action.id}"
    }

    /** The failure action of [cameraID] a page action id names. */
    public fun action(id: String, cameraID: String, model: CamerasModel): RecoveryAction? =
        model.state.value.failures[cameraID]?.failure?.actions?.firstOrNull { id(it) == id }

    public fun pairing(model: CamerasModel, context: Context? = null): MonitorPairingPresentation {
        val state = model.state.value
        val selected = state.nearby.firstOrNull { it.id == state.selection }
        val cameraID = selected?.let { ConnectTarget.Nearby(it.camera).cameraID }
        val attempt = cameraID?.let { state.attempts[it] }
        val failure = cameraID?.let { state.failures[it] }?.failure
        val plan = attempt?.progress?.steps.orEmpty()
        val current = attempt?.progress?.let { 1 + it.current } ?: 0
        val choosing = attempt == null
        val step = attempt?.progress?.let { it.steps.getOrNull(it.current) }
        val instructions = when (val prompt = attempt?.prompt) {
            is ConnectionPrompt.ApproveOnCamera ->
                listOf(MonitorPairingInstruction(lstr(context, R.string.appkit_on_the_camera), MonitorPairingInstructionIcon.CAMERA, listOf(prompt.text)))
            is ConnectionPrompt.AcceptNetworkJoin ->
                listOf(MonitorPairingInstruction(lstr(context, R.string.appkit_on_this_phone), MonitorPairingInstructionIcon.PHONE, listOf(prompt.text)))
            is ConnectionPrompt.Integration ->
                listOf(MonitorPairingInstruction(lstr(context, R.string.appkit_next), MonitorPairingInstructionIcon.CAMERA, listOf(prompt.text)))
            null -> emptyList()
        }
        val settings = failure?.actions?.contains(RecoveryAction.OpenSettings) == true
        return MonitorPairingPresentation(
            steps = listOf(MonitorPairingStep(lstr(context, R.string.appkit_find_your_camera), lstr(context, R.string.appkit_nearby_cameras))) + plan.map { MonitorPairingStep(it.title, it.detail) },
            currentStep = current,
            title = failure?.title ?: step?.title ?: lstr(context, R.string.appkit_find_your_camera),
            body = failure?.message ?: step?.detail ?: lstr(context, R.string.appkit_turn_camera_on),
            target = selected?.camera?.name ?: lstr(context, R.string.appkit_nothing_selected_yet),
            hint = if (choosing) {
                selected?.let { lstr(context, R.string.appkit_selected_camera, it.camera.name) } ?: lstr(context, R.string.appkit_choose_camera)
            } else {
                lstr(context, R.string.appkit_monitoring_opens)
            },
            // The prompt has its own card; the live line is the integration's note or step.
            progress = attempt?.let { it.progress?.note ?: step?.title ?: lstr(context, R.string.appkit_connecting) },
            devices = if (choosing) {
                state.nearby.map {
                    MonitorPairingDevice(
                        it.id, it.camera.name, lstr(context, R.string.appkit_camera_model_nearby, it.camera.model), selected = state.selection == it.id,
                        busy = state.attempts.isNotEmpty(), signalBars = it.camera.signalBars,
                    )
                }
            } else {
                emptyList()
            },
            instructions = instructions,
            emptyTitle = if (choosing && state.nearby.isEmpty()) {
                if (state.scanning) lstr(context, R.string.appkit_looking_for_cameras) else lstr(context, R.string.appkit_no_cameras_yet)
            } else {
                null
            },
            primaryAction = when {
                failure != null -> if (settings) lstr(context, R.string.appkit_open_settings) else lstr(context, R.string.appkit_try_again)
                choosing -> lstr(context, R.string.appkit_continue)
                else -> null
            },
            primaryActionEnabled = failure != null || (choosing && selected != null),
            backAction = when {
                !choosing -> lstr(context, R.string.appkit_cancel)
                state.saved.isEmpty() -> null
                else -> lstr(context, R.string.appkit_back)
            },
        )
    }

    internal fun device(sighting: CamerasModel.Sighting, state: CamerasModel.State, model: CamerasModel, context: Context? = null): CameraItem {
        val camera = sighting.camera
        val held = camera.availability != Availability.Available
        val copy = model.entries.firstOrNull { it.integration.id == sighting.integration }?.slots?.nearbyCard?.invoke(camera)
        return CameraItem(
            id = camera.id,
            name = copy?.name ?: camera.name,
            subtitle = copy?.subtitle ?: if (camera.model.isEmpty()) lstr(context, R.string.appkit_nearby_generic) else lstr(context, R.string.appkit_camera_model_nearby, camera.model),
            badge = if (held) lstr(context, R.string.appkit_badge_in_use) else lstr(context, R.string.appkit_badge_new),
            status = if (held) lstr(context, R.string.appkit_status_in_use) else copy?.status ?: lstr(context, R.string.appkit_status_ready_to_pair),
            actionTitle = lstr(context, R.string.appkit_action_pair),
            isPrimary = state.selection == camera.id,
            isBusy = ConnectTarget.Nearby(camera).cameraID in state.attempts,
            isAvailable = !held,
            signalBars = camera.signalBars,
        )
    }

    internal fun status(attempt: CamerasModel.Attempt, context: Context? = null): String {
        val progress = attempt.progress ?: return lstr(context, R.string.appkit_connecting)
        return attempt.prompt?.text ?: progress.note ?: progress.steps.getOrNull(progress.current)?.title ?: lstr(context, R.string.appkit_connecting)
    }

    internal fun matches(sighting: CamerasModel.Sighting, camera: SavedCamera): Boolean =
        sighting.integration == camera.integration && (sighting.camera.savedID == camera.id || sighting.camera.id == camera.id)

    private fun sighting(camera: SavedCamera, state: CamerasModel.State) = state.nearby.firstOrNull { matches(it, camera) }

    /** The routes the camera's profile (matched by model title) can add; without one, any the integration can add. */
    private fun addableRoutes(camera: SavedCamera, model: CamerasModel): Set<RouteKind> {
        val profiles = model.entries.firstOrNull { it.integration.id == camera.integration }?.integration?.profiles ?: return emptySet()
        return profiles.firstOrNull { it.title == camera.model }?.addableRoutes ?: profiles.flatMapTo(mutableSetOf()) { it.addableRoutes }
    }

    private fun route(target: ConnectTarget): ConnectionRouteID? = (target as? ConnectTarget.Saved)?.route
}
