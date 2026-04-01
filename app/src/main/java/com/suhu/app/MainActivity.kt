package com.suhu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.suhu.app.ui.theme.SuhuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Edge-to-edge configuration untuk status bar transparan
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            SuhuTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SuhuTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = "Suhu Subscription Hunter",
                        style = SuhuTheme.typography.titleLarge.copy(color = SuhuTheme.colors.onBackground)
                    )
                }
            }
        }
    }
}
