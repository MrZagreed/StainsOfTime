package io.github.mrzagreed.stainsoftime.transport

data class TransportState(
    val supported: Boolean,
    val active: Boolean,
    val detail: String
)
