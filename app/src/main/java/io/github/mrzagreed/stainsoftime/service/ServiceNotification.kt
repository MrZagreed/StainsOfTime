package io.github.mrzagreed.stainsoftime.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import io.github.mrzagreed.stainsoftime.core.state.TetherRuntimeState

class ServiceNotification(private val context: Context) {

    fun build(state: TetherRuntimeState): Notification {
        ensureChannel()
        val contentText = if (state.localProxyRunning) {
            "${state.activeMode.displayName} mode · proxy on · sessions ${state.activeSessionCount}"
        } else {
            "${state.activeMode.displayName} mode · proxy off · sessions ${state.activeSessionCount}"
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("StainsOfTime service")
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.stat_sys_tether_general)
            .setOngoing(true)
            .build()
    }

    private fun ensureChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java) ?: return
        val existing = manager.getNotificationChannel(CHANNEL_ID)
        if (existing != null) return

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                "StainsOfTime",
                NotificationManager.IMPORTANCE_LOW
            )
        )
    }

    companion object {
        const val CHANNEL_ID = "stains_service"
        const val NOTIFICATION_ID = 1001
    }
}
