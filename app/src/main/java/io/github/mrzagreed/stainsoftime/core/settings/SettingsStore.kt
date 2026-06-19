package io.github.mrzagreed.stainsoftime.core.settings

import android.content.Context
import io.github.mrzagreed.stainsoftime.core.TetherMode

class SettingsStore(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): AppSettings {
        val modeName = prefs.getString(KEY_MODE, TetherMode.WIFI.name).orEmpty()
        val mode = TetherMode.entries.firstOrNull { it.name == modeName } ?: TetherMode.WIFI
        return AppSettings(
            selectedMode = mode,
            localProxyEnabled = prefs.getBoolean(KEY_LOCAL_PROXY_ENABLED, false),
            localProxyPort = prefs.getInt(KEY_LOCAL_PROXY_PORT, 8000)
        )
    }

    fun save(settings: AppSettings) {
        prefs.edit()
            .putString(KEY_MODE, settings.selectedMode.name)
            .putBoolean(KEY_LOCAL_PROXY_ENABLED, settings.localProxyEnabled)
            .putInt(KEY_LOCAL_PROXY_PORT, settings.localProxyPort)
            .apply()
    }

    fun setMode(mode: TetherMode) {
        save(load().copy(selectedMode = mode))
    }

    fun setLocalProxyEnabled(enabled: Boolean) {
        save(load().copy(localProxyEnabled = enabled))
    }

    companion object {
        private const val PREFS_NAME = "stains_of_time_settings"
        private const val KEY_MODE = "selected_mode"
        private const val KEY_LOCAL_PROXY_ENABLED = "local_proxy_enabled"
        private const val KEY_LOCAL_PROXY_PORT = "local_proxy_port"
    }
}
