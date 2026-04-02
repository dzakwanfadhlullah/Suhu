package com.suhu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.core.view.WindowCompat
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.navigation.SuhuNavHost
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Edge-to-edge configuration untuk status bar transparan
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            SuhuTheme {
                SuhuNavHost()
            }
        }
    }
}
