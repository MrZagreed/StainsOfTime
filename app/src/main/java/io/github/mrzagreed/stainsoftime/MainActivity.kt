package io.github.mrzagreed.stainsoftime

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import io.github.mrzagreed.stainsoftime.core.TetherMode
import io.github.mrzagreed.stainsoftime.core.settings.SettingsStore
import io.github.mrzagreed.stainsoftime.core.state.RuntimeStateStore
import io.github.mrzagreed.stainsoftime.service.ServiceCommand

class MainActivity : ComponentActivity() {

    private lateinit var settingsStore: SettingsStore
    private lateinit var infoView: TextView
    private lateinit var proxyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsStore = SettingsStore(applicationContext)

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 64, 48, 64)
        }

        val title = TextView(this).apply {
            text = "StainsOfTime"
            textSize = 24f
        }

        infoView = TextView(this)

        val wifiButton = Button(this).apply {
            text = "Wi-Fi"
            setOnClickListener { selectMode(TetherMode.WIFI) }
        }

        val usbButton = Button(this).apply {
            text = "USB"
            setOnClickListener { selectMode(TetherMode.USB) }
        }

        val bluetoothButton = Button(this).apply {
            text = "Bluetooth"
            setOnClickListener { selectMode(TetherMode.BLUETOOTH) }
        }

        proxyButton = Button(this).apply {
            setOnClickListener { toggleProxy() }
        }

        val refreshButton = Button(this).apply {
            text = "Refresh"
            setOnClickListener { render() }
        }

        container.addView(title)
        container.addView(infoView)
        container.addView(wifiButton)
        container.addView(usbButton)
        container.addView(bluetoothButton)
        container.addView(proxyButton)
        container.addView(refreshButton)
        setContentView(container)

        render()
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun selectMode(mode: TetherMode) {
        settingsStore.setMode(mode)
        ServiceCommand.setMode(this, mode)
        render()
    }

    private fun toggleProxy() {
        val settings = settingsStore.load()
        val enabled = !settings.localProxyEnabled
        settingsStore.setLocalProxyEnabled(enabled)
        if (enabled) {
            ServiceCommand.startProxy(this)
        } else {
            ServiceCommand.stopProxy(this)
        }
        render()
    }

    private fun render() {
        val settings = settingsStore.load()
        val runtime = RuntimeStateStore.get()
        infoView.text = "Selected: ${settings.selectedMode.displayName}\nRunning: ${runtime.activeMode.displayName}\nProxy enabled: ${settings.localProxyEnabled}\nProxy running: ${runtime.localProxyRunning}\nSessions: ${runtime.activeSessionCount}"
        proxyButton.text = if (settings.localProxyEnabled) "Stop local proxy" else "Start local proxy"
    }
}
