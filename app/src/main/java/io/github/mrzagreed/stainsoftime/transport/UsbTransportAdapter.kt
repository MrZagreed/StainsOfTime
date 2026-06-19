package io.github.mrzagreed.stainsoftime.transport

import android.content.Context
import io.github.mrzagreed.stainsoftime.core.TetherMode

class UsbTransportAdapter(context: Context) : TetherTransportAdapter {

    private val appContext = context.applicationContext
    private var active = false

    override val mode: TetherMode = TetherMode.USB

    override fun probe(): TransportState {
        return TransportState(supported = true, active = active, detail = "Usb probe placeholder")
    }

    override fun start(): TransportState {
        active = true
        return TransportState(supported = true, active = active, detail = "Usb active")
    }

    override fun stop(): TransportState {
        active = false
        return TransportState(supported = true, active = false, detail = "Usb stopped")
    }
}
