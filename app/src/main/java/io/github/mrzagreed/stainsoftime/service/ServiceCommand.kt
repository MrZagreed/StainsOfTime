package io.github.mrzagreed.stainsoftime.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import io.github.mrzagreed.stainsoftime.core.TetherMode

object ServiceCommand {
    private const val ACTION_SET_MODE = "io.github.mrzagreed.stainsoftime.action.SET_MODE"
    private const val ACTION_START_PROXY = "io.github.mrzagreed.stainsoftime.action.START_PROXY"
    private const val ACTION_STOP_PROXY = "io.github.mrzagreed.stainsoftime.action.STOP_PROXY"
    private const val EXTRA_MODE = "extra_mode"

    fun setMode(context: Context, mode: TetherMode) {
        dispatch(context, ACTION_SET_MODE) {
            putExtra(EXTRA_MODE, mode.name)
        }
    }

    fun startProxy(context: Context) {
        dispatch(context, ACTION_START_PROXY)
    }

    fun stopProxy(context: Context) {
        dispatch(context, ACTION_STOP_PROXY)
    }

    internal fun actionSetMode(): String = ACTION_SET_MODE
    internal fun actionStartProxy(): String = ACTION_START_PROXY
    internal fun actionStopProxy(): String = ACTION_STOP_PROXY

    internal fun readMode(intent: Intent): TetherMode? {
        val name = intent.getStringExtra(EXTRA_MODE) ?: return null
        return TetherMode.entries.firstOrNull { it.name == name }
    }

    private inline fun dispatch(
        context: Context,
        action: String,
        block: Intent.() -> Unit = {}
    ) {
        val intent = Intent(context, TetherCoordinatorService::class.java).apply {
            this.action = action
            block()
        }
        ContextCompat.startForegroundService(context, intent)
    }
}
