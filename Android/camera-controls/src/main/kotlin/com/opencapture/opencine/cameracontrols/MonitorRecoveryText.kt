package com.opencapture.opencine.cameracontrols

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.opencapture.opencine.vocabulary.SessionDropSuspect
import com.opencapture.opencine.vocabulary.SessionRecoveryPolicy
import com.opencapture.opencine.vocabulary.SessionRecoveryUi

/**
 * The recovery card's copy in the operator's language. [com.opencapture.opencine.vocabulary.SessionRecoveryCopy]
 * stays the shared English source for the wire-free vocabulary module; this layer only draws the same four
 * fields from resources so a translated shell reads them, and keeps its branch order in step with it.
 */
internal data class MonitorRecoveryText(
    val title: String,
    val detail: String,
    val retry: String,
    val operatorMenu: String,
)

@Composable
internal fun recoveryText(
    state: SessionRecoveryUi,
    cameraName: String,
    dropSuspect: SessionDropSuspect?,
): MonitorRecoveryText {
    val name = cameraName.trim()
    val camera = if (name.isEmpty()) stringResource(R.string.recovery_camera) else name
    val title = when (state) {
        SessionRecoveryUi.Idle -> ""
        is SessionRecoveryUi.Retrying -> stringResource(R.string.recovery_title_retrying)
        is SessionRecoveryUi.WaitingForOperator -> stringResource(R.string.recovery_title_disconnected)
        is SessionRecoveryUi.PausedAfterDrops -> stringResource(R.string.recovery_title_keeps_dropping)
        is SessionRecoveryUi.TakenOver -> stringResource(R.string.recovery_title_taken_over)
    }
    val detail = when (state) {
        SessionRecoveryUi.Idle -> ""
        // The episode ends on its time budget, usually before maxAttempts: "attempt 1 of 8"
        // followed by "after 3 tries" read as two machines (hardware pass 5.1).
        is SessionRecoveryUi.Retrying -> stringResource(
            R.string.recovery_detail_retrying, camera, state.attempt,
            SessionRecoveryPolicy.AUTOMATIC_RECOVERY_BUDGET_MS / 60_000,
        )
        is SessionRecoveryUi.WaitingForOperator -> {
            val tries = if (state.attemptsMade == 1) stringResource(R.string.recovery_try_once)
            else stringResource(R.string.recovery_tries, state.attemptsMade)
            stringResource(R.string.recovery_detail_waiting, camera, tries)
        }
        is SessionRecoveryUi.PausedAfterDrops -> when (dropSuspect) {
            null -> stringResource(R.string.recovery_detail_paused, camera, state.drops)
            SessionDropSuspect.OTHER_DEVICE_SCANNING ->
                stringResource(R.string.recovery_detail_paused_scanning, camera, state.drops)
        }
        is SessionRecoveryUi.TakenOver -> {
            val other = state.holder?.trim()?.takeIf(String::isNotEmpty)
                ?: stringResource(R.string.recovery_other_device)
            stringResource(R.string.recovery_detail_taken_over, other, name.ifEmpty { camera })
        }
    }
    return MonitorRecoveryText(
        title = title,
        detail = detail,
        retry = stringResource(R.string.recovery_retry),
        operatorMenu = stringResource(R.string.recovery_operator_menu),
    )
}
