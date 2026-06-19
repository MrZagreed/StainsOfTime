package io.github.mrzagreed.stainsoftime.core.device

data class PeerDevice(
    val sessionId: String,
    val displayName: String,
    val address: String,
    val connectedAtEpochMs: Long = System.currentTimeMillis()
)
