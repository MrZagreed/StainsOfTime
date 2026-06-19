package io.github.mrzagreed.stainsoftime.proxy.model

data class HttpProxyRequest(
    val method: String,
    val target: String,
    val version: String,
    val headers: Map<String, String>
)
