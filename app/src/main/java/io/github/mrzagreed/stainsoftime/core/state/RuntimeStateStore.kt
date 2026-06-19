package io.github.mrzagreed.stainsoftime.core.state

object RuntimeStateStore {
    @Volatile
    private var current: TetherRuntimeState = TetherRuntimeState()

    fun get(): TetherRuntimeState = current

    @Synchronized
    fun update(transform: (TetherRuntimeState) -> TetherRuntimeState) {
        current = transform(current)
    }

    @Synchronized
    fun replace(state: TetherRuntimeState) {
        current = state
    }
}
