package io.github.mrzagreed.stainsoftime.proxy

import java.io.Closeable
import java.net.ServerSocket
import java.util.concurrent.atomic.AtomicBoolean

class LocalProxyServer(
    private val listenPort: Int = 8000
) : Closeable {

    private var serverSocket: ServerSocket? = null
    private val running = AtomicBoolean(false)
    private var worker: Thread? = null

    fun start() {
        if (!running.compareAndSet(false, true)) return

        worker = Thread {
            serverSocket = ServerSocket(listenPort)
            while (running.get()) {
                val client = try {
                    serverSocket?.accept()
                } catch (_: Exception) {
                    null
                }
                client?.close()
            }
        }.apply {
            name = "LocalProxyServer"
            isDaemon = true
            start()
        }
    }

    override fun close() {
        running.set(false)
        try {
            serverSocket?.close()
        } catch (_: Exception) {
        }
        worker = null
        serverSocket = null
    }
}
