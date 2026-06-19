package io.github.mrzagreed.stainsoftime

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import io.github.mrzagreed.stainsoftime.core.TetherMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 64, 48, 64)
        }

        val title = TextView(this).apply {
            text = "StainsOfTime"
            textSize = 24f
        }

        val subtitle = TextView(this).apply {
            text = "Clean-room Android rewrite bootstrap. Current default mode: ${TetherMode.WIFI_DISPLAY_NAME}."
            textSize = 16f
        }

        container.addView(title)
        container.addView(subtitle)
        setContentView(container)
    }
}
