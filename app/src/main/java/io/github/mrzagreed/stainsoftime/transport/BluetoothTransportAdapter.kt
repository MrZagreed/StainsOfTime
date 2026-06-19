package io.github.mrzagreed.stainsoftime.transport

import android.bluetooth.BluetoothAdapter
import io.github.mrzagreed.stainsoftime.core.TetherMode

class BluetoothTransportAdapter : TetherTransportAdapter {

    private var active = false

    override val mode: TetherMode = TetherMode.BLUETOOTH

    override fun probe(): TransportState {
        val supported = BluetoothAdapter.getDefaultAdapter() != null
        return TransportState(supported = supported, active = active, detail = if (supported) "Bluetooth available" else "Bluetooth unavailable")
    }

    override fun start(): TransportState {
        val supported = BluetoothAdapter.getDefaultAdapter() != null
        active = supported
        return TransportState(supported = supported, active = active, detail = if (supported) "Bluetooth active" else "Bluetooth unsupported")
    }

    override fun stop(): TransportState {
        val supported = BluetoothAdapter.getDefaultAdapter() != null
        active = false
        return TransportState(supported = supported, active = false, detail = "Bluetooth stopped")
    }
}
