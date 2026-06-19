package io.github.mrzagreed.stainsoftime.transport

import android.content.Context
import android.net.wifi.WifiManager
import io.github.mrzagreed.stainsoftime.core.TetherMode

class WifiTransportAdapter(context: Context) : TetherTransportAdapter {

    private val wifiManager = context.applicationContext.getSystemService(WifiManager::class.java)
    private var active = false

    override val mode: TetherMode = TetherMode.WIFI

    override fun probe(): TransportState {
        val supported = wifiManager != null
        return TransportState(supported = supported, active = active, detail = if (supported) "Wifi available" else "Wifi unavailable")
    }

    override fun start(): TransportState {
        val supported = wifiManager != null
        active = supported
        return TransportState(supported = supported, active = active, detail = if (supported) "Wifi active" else "Wifi unsupported")
    }

    override fun stop(): TransportState {
        val supported = wifiManager != null
        active = false
        return TransportState(supported = supported, active = false, detail = "Wifi stopped")
    }
}
