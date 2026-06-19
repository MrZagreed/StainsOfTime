package io.github.mrzagreed.stainsoftime.core

enum class TetherMode(
    val wireProtocolName: String,
    val displayName: String
) {
    USB("usb", "USB"),
    BLUETOOTH("bluetooth", "Bluetooth"),
    WIFI("wifi", WIFI_DISPLAY_NAME);

    companion object {
        const val WIFI_DISPLAY_NAME: String = "Wi-Fi"
    }
}
