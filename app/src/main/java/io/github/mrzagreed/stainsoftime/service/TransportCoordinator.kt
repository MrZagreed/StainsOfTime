package io.github.mrzagreed.stainsoftime.service

import io.github.mrzagreed.stainsoftime.core.TetherMode
import io.github.mrzagreed.stainsoftime.transport.TetherTransportAdapter
import io.github.mrzagreed.stainsoftime.transport.TransportState

class TransportCoordinator(adapters: List<TetherTransportAdapter>) {

    private val adaptersByMode = adapters.associateBy { it.mode }

    fun probe(mode: TetherMode): TransportState {
        return adaptersByMode.getValue(mode).probe()
    }

    fun start(mode: TetherMode): TransportState {
        return adaptersByMode.getValue(mode).start()
    }

    fun stop(mode: TetherMode): TransportState {
        return adaptersByMode.getValue(mode).stop()
    }
}
