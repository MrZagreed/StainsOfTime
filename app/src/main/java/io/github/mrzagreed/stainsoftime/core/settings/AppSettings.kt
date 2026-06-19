package io.github.mrzagreed.stainsoftime.core.settings

import io.github.mrzagreed.stainsoftime.core.TetherMode

data class AppSettings(
    val selectedMode: TetherMode = TetherMode.WIFI,
    val localProxyEnabled: Boolean = false,
    val localProxyPort: Int = 8000
)
