package io.github.mrzagreed.stainsoftime.core.device

class DeviceRegistry {

    private val devices = linkedMapOf<String, PeerDevice>()

    @Synchronized
    fun upsert(device: PeerDevice) {
        devices[device.sessionId] = device
    }

    @Synchronized
    fun remove(sessionId: String) {
        devices.remove(sessionId)
    }

    @Synchronized
    fun snapshot(): List<PeerDevice> = devices.values.toList()

    @Synchronized
    fun clear() {
        devices.clear()
    }
}
