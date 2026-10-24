package com.opencapture.opencine.appkit

import android.content.Context
import androidx.compose.runtime.Composable
import com.opencapture.monitorui.MonitorPairingCheck
import com.opencapture.monitorui.MonitorPairingCheckState
import com.opencapture.monitorui.MonitorPairingDevice
import com.opencapture.monitorui.MonitorPairingInstruction
import com.opencapture.monitorui.MonitorPairingInstructionIcon
import com.opencapture.monitorui.MonitorPairingPresentation
import com.opencapture.monitorui.MonitorPairingStep
import com.opencapture.opencine.core.ConnectTarget
import com.opencapture.opencine.core.ConnectionPhase
import com.opencapture.opencine.core.LocalVPNFilter

/**
 * The four-page pair wizard the camera apps showed before the shared pair page (Swift
 * `PairingWizard`): find, approve, join camera Wi-Fi, open the video link, with a body and cards per
 * page, a check list from the Wi-Fi page on, the connection's live label and a failure as the page's
 * error. The app gives its copy and what only it knows. On Android, [blocker] is the primary action
 * while discovery cannot run (Nearby devices, Settings or Bluetooth), and the app's callouts are the
 * flow's content.
 */
public data class PairingWizard(
    /** One body per page. */
    val bodies: List<String>,
    /** "On the camera" lines on the approve page and on the Wi-Fi page. */
    val approveLines: List<String>,
    val joinLines: List<String>,
    /** The card while choosing; null shows none. */
    val choosing: MonitorPairingInstruction? = null,
    /** The connection's live label, shown while it scans or connects. */
    val label: String? = null,
    val joinedNetwork: String? = null,
    val vpnActive: Boolean = false,
    val blocker: String? = null,
) {
    public companion object {
        internal fun titles(context: Context? = null): List<String> = listOf(
            lstr(context, R.string.appkit_wizard_title_find),
            lstr(context, R.string.appkit_wizard_title_approve),
            lstr(context, R.string.appkit_wizard_title_join),
            lstr(context, R.string.appkit_wizard_title_video),
        )
        internal fun rail(context: Context? = null): List<String> = listOf(
            lstr(context, R.string.appkit_wizard_rail_bluetooth),
            lstr(context, R.string.appkit_wizard_rail_prompt),
            lstr(context, R.string.appkit_wizard_rail_network),
            lstr(context, R.string.appkit_wizard_rail_video),
        )

        /**
         * The apps' live label for a connection phase (Swift `ConnectionPhase.label`). Reads each
         * integration's phase enum by its constant names, which match by design.
         */
        public fun phaseLabel(phase: Enum<*>): String = when (phase.name) {
            "SCANNING" -> "Scanning for camera…"
            "CONNECTING_GATT" -> "Connecting (Bluetooth)…"
            "PAIRING" -> "Pairing…"
            "AWAITING_APPROVAL" -> "Approve on the camera screen"
            "READING_WIFI_CREDS" -> "Reading Wi-Fi credentials…"
            "JOINING_WIFI" -> "Joining camera Wi-Fi…"
            "OPENING_DATALINK" -> "Opening datalink…"
            "LIVE" -> "Connected"
            else -> "Idle"
        }

        /** A connect's scan (a first step with a live note, as both integrations report it) is still page 0. */
        internal fun page(attempt: CamerasModel.Attempt?): Int {
            attempt ?: return 0
            val progress = attempt.progress ?: return 1
            return when (progress.current) {
                0 -> if (progress.phase == ConnectionPhase.LINK && progress.note != null) 0 else 1
                1 -> 2
                else -> 3
            }
        }
    }

    /** The wizard with its actions: Continue connects the selection; [blocker] calls [onBlocker]; a failure's Try again goes back to choosing and calls [rescan]; Cancel stops the connect and calls [onDone]. */
    public fun flow(
        model: CamerasModel,
        onDone: () -> Unit,
        rescan: () -> Unit,
        onBlocker: () -> Unit = {},
        context: Context? = null,
        content: @Composable () -> Unit = {},
    ): IntegrationPairingFlow = IntegrationPairingFlow(
        presentation = CamerasMapping.wizard(model, this, context),
        onSelect = { id -> if (model.state.value.attempts.isEmpty()) model.select(id) },
        onPrimary = primary@{
            val state = model.state.value
            if (state.attempts.isNotEmpty()) return@primary
            val sighting = state.nearby.firstOrNull { it.id == state.selection }
            val failure = model.failureFor(state.selection)
            when {
                failure != null -> {
                    model.dismissFailure(failure.target.cameraID)
                    rescan()
                }
                blocker != null -> onBlocker()
                sighting != null -> model.connect(sighting)
            }
        },
        onBack = {
            model.cancelAll()
            onDone()
        },
        content = content,
    )
}

/** The selected camera's connect as the wizard shows it; any running attempt makes it busy (Swift `CamerasMapping.wizard`). */
public fun CamerasMapping.wizard(model: CamerasModel, wizard: PairingWizard, context: Context? = null): MonitorPairingPresentation {
    val state = model.state.value
    val selected = state.nearby.firstOrNull { it.id == state.selection }
    val cameraID = selected?.let { ConnectTarget.Nearby(it.camera).cameraID }
    val busy = state.attempts.isNotEmpty()
    val failed = if (busy) null else model.failureFor(state.selection)
    val failure = failed?.failure
    val page = if (failure != null) 0 else PairingWizard.page(cameraID?.let { state.attempts[it] })
    val choosing = page == 0
    val instructions = when (page) {
        0 -> listOfNotNull(wizard.choosing.takeIf { failure == null })
        1 -> listOf(
            MonitorPairingInstruction(lstr(context, R.string.appkit_on_the_camera), MonitorPairingInstructionIcon.CAMERA, wizard.approveLines),
            MonitorPairingInstruction(
                lstr(context, R.string.appkit_on_this_phone), MonitorPairingInstructionIcon.PHONE,
                listOf(lstr(context, R.string.appkit_wizard_wait_bt), lstr(context, R.string.appkit_wizard_no_quit)),
            ),
        )
        2 -> listOf(
            MonitorPairingInstruction(lstr(context, R.string.appkit_on_the_camera), MonitorPairingInstructionIcon.CAMERA, wizard.joinLines),
            MonitorPairingInstruction(
                lstr(context, R.string.appkit_on_this_phone), MonitorPairingInstructionIcon.PHONE,
                listOf(lstr(context, R.string.appkit_wizard_tap_join), LocalVPNFilter.JOIN_WIFI_PHONE_STEP),
            ),
        )
        else -> if (wizard.vpnActive) {
            listOf(MonitorPairingInstruction(lstr(context, R.string.appkit_wizard_check_connection), MonitorPairingInstructionIcon.PHONE, listOf(LocalVPNFilter.WIZARD_BANNER)))
        } else {
            emptyList()
        }
    }
    val failedName = when (val target = failed?.target) {
        is ConnectTarget.Nearby -> target.camera.name
        is ConnectTarget.Saved -> target.camera.displayName
        is ConnectTarget.AddRoute -> target.camera.displayName
        null -> null
    }
    val name = selected?.let { device(it, state, model).name } ?: failedName
    val checks = if (page >= 2) {
        listOf(
            MonitorPairingCheck(lstr(context, R.string.appkit_wizard_bt_link), name ?: lstr(context, R.string.appkit_wizard_camera_connected), MonitorPairingCheckState.COMPLETE, lstr(context, R.string.appkit_wizard_ok)),
            MonitorPairingCheck(
                lstr(context, R.string.appkit_route_camera_wifi), wizard.joinedNetwork ?: lstr(context, R.string.appkit_wizard_waiting_network),
                if (page > 2) MonitorPairingCheckState.COMPLETE else MonitorPairingCheckState.ACTIVE,
                if (page > 2) lstr(context, R.string.appkit_wizard_ok) else lstr(context, R.string.appkit_wizard_joining),
            ),
            MonitorPairingCheck(
                lstr(context, R.string.appkit_wizard_live_picture), if (page > 2) lstr(context, R.string.appkit_wizard_opening_video) else lstr(context, R.string.appkit_wizard_starts_after),
                if (page > 2) MonitorPairingCheckState.ACTIVE else MonitorPairingCheckState.WAITING, lstr(context, R.string.appkit_wizard_waiting),
            ),
        )
    } else {
        emptyList()
    }
    val hint = when (page) {
        0 -> name?.let { lstr(context, R.string.appkit_selected_camera, it) } ?: lstr(context, R.string.appkit_choose_camera)
        1 -> lstr(context, R.string.appkit_wizard_nothing_type)
        2 -> lstr(context, R.string.appkit_wizard_android_asks)
        else -> lstr(context, R.string.appkit_monitoring_opens)
    }
    val (primary, enabled) = when {
        failure != null -> lstr(context, R.string.appkit_try_again) to true
        wizard.blocker != null -> wizard.blocker to true
        choosing -> lstr(context, R.string.appkit_continue) to (selected != null && !busy)
        else -> null to false
    }
    return MonitorPairingPresentation(
        steps = PairingWizard.titles(context).zip(PairingWizard.rail(context)) { title, rail -> MonitorPairingStep(title, rail) },
        currentStep = page,
        title = PairingWizard.titles(context)[page],
        body = wizard.bodies[page],
        target = name ?: lstr(context, R.string.appkit_nothing_selected_yet),
        hint = hint,
        // A blocker means discovery is not running (main's scan never started).
        progress = wizard.label.takeIf { failure == null && (busy || state.scanning && wizard.blocker == null) },
        error = failure?.message,
        devices = if (choosing) {
            state.nearby.map {
                val item = device(it, state, model)
                MonitorPairingDevice(it.id, item.name, item.subtitle, selected = item.isPrimary, busy = busy, signalBars = item.signalBars)
            }
        } else {
            emptyList()
        },
        instructions = instructions,
        checks = checks,
        emptyTitle = if (choosing && state.nearby.isEmpty() && wizard.blocker == null) {
            if (state.scanning) lstr(context, R.string.appkit_looking_for_cameras) else lstr(context, R.string.appkit_no_cameras_yet)
        } else {
            null
        },
        primaryAction = primary,
        primaryActionEnabled = enabled,
        backAction = if (busy) lstr(context, R.string.appkit_cancel) else null,
    )
}
