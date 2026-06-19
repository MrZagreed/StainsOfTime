package io.github.mrzagreed.stainsoftime.core.session

import io.github.mrzagreed.stainsoftime.core.TetherMode

data class ClientSession(
    val id: String,
    val mode: TetherMode,
    val peerAddress: String,
    val bytesUp: Long = 0,
    val bytesDown: Long = 0,
    val connectedAtEpochMs: Long = System.currentTimeMillis()
)
