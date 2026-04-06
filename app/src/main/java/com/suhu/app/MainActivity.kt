package com.suhu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.app.NotificationManagerCompat
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.navigation.SuhuNavHost

val LocalNotificationAccess = staticCompositionLocalOf<Boolean> { true }

class MainActivity : ComponentActivity() {
    
    private val _isNotificationAccessGranted = MutableStateFlow(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Edge-to-edge configuration untuk status bar transparan
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            val isGranted = _isNotificationAccessGranted.collectAsState().value
            SuhuTheme {
                CompositionLocalProvider(LocalNotificationAccess provides isGranted) {
                    SuhuNavHost()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Strategi Keep-Alive: Secara berkala memaksa OS merebind ulang NotificationListener saat user membuka aplikasi
        com.suhu.app.util.ServiceKeeper.toggleNotificationListenerService(this)
        
        // EDGE CASE: Deteksi Izin Notification Listener Dicabut Aksesnya secara Paksa
        val enabledListeners = NotificationManagerCompat.getEnabledListenerPackages(this)
        _isNotificationAccessGranted.value = enabledListeners.contains(packageName)
    }
}
