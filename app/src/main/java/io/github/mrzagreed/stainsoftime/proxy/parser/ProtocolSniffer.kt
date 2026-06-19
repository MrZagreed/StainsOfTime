package io.github.mrzagreed.stainsoftime.proxy.parser

import io.github.mrzagreed.stainsoftime.proxy.ProxyProtocol

object ProtocolSniffer {
    fun sniff(first: Int, second: Int): ProxyProtocol {
        return when {
            first == 'G'.code || first == 'P'.code -> ProxyProtocol.HTTP
            first == 'C'.code -> ProxyProtocol.HTTPS_CONNECT
            first == 0x04 -> ProxyProtocol.SOCKS4
            first == 0x05 -> ProxyProtocol.SOCKS5
            else -> ProxyProtocol.UNKNOWN
        }
    }
}
