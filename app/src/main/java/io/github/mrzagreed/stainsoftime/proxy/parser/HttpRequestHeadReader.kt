package io.github.mrzagreed.stainsoftime.proxy.parser

import io.github.mrzagreed.stainsoftime.proxy.model.HttpProxyRequest
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

class HttpRequestHeadReader(
    private val maxBytes: Int = 16 * 1024
) {

    fun read(inputStream: InputStream, prefix: ByteArray = byteArrayOf()): HttpProxyRequest {
        val buffer = ByteArrayOutputStream()
        if (prefix.isNotEmpty()) {
            buffer.write(prefix)
        }

        while (buffer.size() < maxBytes) {
            val next = inputStream.read()
            if (next < 0) break
            buffer.write(next)
            val text = buffer.toString(StandardCharsets.ISO_8859_1.name())
            if (text.contains("\r\n\r\n")) {
                return parse(text)
            }
        }

        return parse(buffer.toString(StandardCharsets.ISO_8859_1.name()))
    }

    private fun parse(head: String): HttpProxyRequest {
        val lines = head.split("\r\n").filter { it.isNotBlank() }
        val requestLine = lines.firstOrNull().orEmpty().split(' ')
        val method = requestLine.getOrElse(0) { "" }
        val target = requestLine.getOrElse(1) { "" }
        val version = requestLine.getOrElse(2) { "HTTP/1.1" }
        val headers = linkedMapOf<String, String>()
        for (line in lines.drop(1)) {
            val index = line.indexOf(':')
            if (index > 0) {
                headers[line.substring(0, index).trim()] = line.substring(index + 1).trim()
            }
        }
        return HttpProxyRequest(method = method, target = target, version = version, headers = headers)
    }
}
