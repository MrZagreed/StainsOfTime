package io.github.mrzagreed.stainsoftime.core.session

class SessionRegistry {

    private val sessions = linkedMapOf<String, ClientSession>()

    @Synchronized
    fun upsert(session: ClientSession) {
        sessions[session.id] = session
    }

    @Synchronized
    fun remove(sessionId: String) {
        sessions.remove(sessionId)
    }

    @Synchronized
    fun snapshot(): List<ClientSession> = sessions.values.toList()

    @Synchronized
    fun size(): Int = sessions.size

    @Synchronized
    fun clear() {
        sessions.clear()
    }

    @Synchronized
    fun totalBytesUp(): Long = sessions.values.sumOf { it.bytesUp }

    @Synchronized
    fun totalBytesDown(): Long = sessions.values.sumOf { it.bytesDown }
}
