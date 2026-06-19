package io.github.mrzagreed.stainsoftime.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.github.mrzagreed.stainsoftime.proxy.LocalProxyServer

class TetherCoordinatorService : Service() {

    private val binder = LocalBinder()
    private var proxyServer: LocalProxyServer? = null

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        proxyServer = LocalProxyServer()
    }

    override fun onDestroy() {
        proxyServer?.close()
        proxyServer = null
        super.onDestroy()
    }

    fun startLocalProxy() {
        proxyServer?.start()
    }

    inner class LocalBinder : Binder() {
        fun getService(): TetherCoordinatorService = this@TetherCoordinatorService
    }
}
