package io.github.mrzagreed.stainsoftime.core.state

import io.github.mrzagreed.stainsoftime.core.TetherMode

data class TetherRuntimeState(
    val activeMode: TetherMode = TetherMode.WIFI,
    val localProxyRunning: Boolean = false,
    val activeSessionCount: Int = 0,
    val totalBytesUp: Long = 0,
    val totalBytesDown: Long = 0
)
