package io.github.mrzagreed.stainsoftime.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.IBinder
import androidx.core.app.ServiceCompat
import io.github.mrzagreed.stainsoftime.core.settings.SettingsStore
import io.github.mrzagreed.stainsoftime.core.state.RuntimeStateStore
import io.github.mrzagreed.stainsoftime.core.state.TetherRuntimeState
import io.github.mrzagreed.stainsoftime.core.session.SessionRegistry
import io.github.mrzagreed.stainsoftime.proxy.LocalProxyServer

class TetherCoordinatorService : Service() {

    private val binder = LocalBinder()
    private val sessionRegistry = SessionRegistry()

    private lateinit var settingsStore: SettingsStore
    private lateinit var notificationFactory: ServiceNotification

    private var proxyServer: LocalProxyServer? = null

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        settingsStore = SettingsStore(applicationContext)
        notificationFactory = ServiceNotification(this)

        val settings = settingsStore.load()
        RuntimeStateStore.replace(
            TetherRuntimeState(
                activeMode = settings.selectedMode,
                localProxyRunning = false,
                activeSessionCount = sessionRegistry.size(),
                totalBytesUp = sessionRegistry.totalBytesUp(),
                totalBytesDown = sessionRegistry.totalBytesDown()
            )
        )
        ensureForeground()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ensureForeground()

        when (intent?.action) {
            ServiceCommand.actionSetMode() -> {
                ServiceCommand.readMode(intent)?.let { mode ->
                    settingsStore.setMode(mode)
                    RuntimeStateStore.update { it.copy(activeMode = mode) }
                }
            }

            ServiceCommand.actionStartProxy() -> {
                settingsStore.setLocalProxyEnabled(true)
                startLocalProxyInternal()
            }

            ServiceCommand.actionStopProxy() -> {
                settingsStore.setLocalProxyEnabled(false)
                stopLocalProxyInternal()
            }

            else -> {
                if (settingsStore.load().localProxyEnabled) {
                    startLocalProxyInternal()
                }
            }
        }

        refreshNotification()
        return START_STICKY
    }

    override fun onDestroy() {
        stopLocalProxyInternal()
        sessionRegistry.clear()
        RuntimeStateStore.update {
            it.copy(
                localProxyRunning = false,
                activeSessionCount = 0,
                totalBytesUp = 0,
                totalBytesDown = 0
            )
        }
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    fun startLocalProxy() {
        settingsStore.setLocalProxyEnabled(true)
        startLocalProxyInternal()
        refreshNotification()
    }

    fun currentState(): TetherRuntimeState = RuntimeStateStore.get()

    private fun startLocalProxyInternal() {
        if (proxyServer == null) {
            proxyServer = LocalProxyServer(settingsStore.load().localProxyPort)
        }
        proxyServer?.start()
        RuntimeStateStore.update {
            it.copy(
                localProxyRunning = true,
                activeSessionCount = sessionRegistry.size(),
                totalBytesUp = sessionRegistry.totalBytesUp(),
                totalBytesDown = sessionRegistry.totalBytesDown()
            )
        }
    }

    private fun stopLocalProxyInternal() {
        proxyServer?.close()
        proxyServer = null
        RuntimeStateStore.update {
            it.copy(
                localProxyRunning = false,
                activeSessionCount = sessionRegistry.size(),
                totalBytesUp = sessionRegistry.totalBytesUp(),
                totalBytesDown = sessionRegistry.totalBytesDown()
            )
        }
    }

    private fun ensureForeground() {
        ServiceCompat.startForeground(
            this,
            ServiceNotification.NOTIFICATION_ID,
            notificationFactory.build(RuntimeStateStore.get()),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }

    private fun refreshNotification() {
        ServiceCompat.startForeground(
            this,
            ServiceNotification.NOTIFICATION_ID,
            notificationFactory.build(RuntimeStateStore.get()),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }

    inner class LocalBinder : Binder() {
        fun getService(): TetherCoordinatorService = this@TetherCoordinatorService
    }
}
