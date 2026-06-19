package io.github.mrzagreed.stainsoftime.transport

import io.github.mrzagreed.stainsoftime.core.TetherMode

interface TetherTransportAdapter {
    val mode: TetherMode

    fun probe(): TransportState

    fun start(): TransportState

    fun stop(): TransportState
}
