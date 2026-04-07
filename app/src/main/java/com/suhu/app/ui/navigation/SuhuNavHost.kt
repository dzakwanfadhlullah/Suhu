package com.suhu.app.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.suhu.app.ui.screens.DashboardEmptyScreen
import com.suhu.app.ui.screens.ManualEntryFormScreen
import com.suhu.app.ui.screens.OnboardingScreen
import com.suhu.app.ui.theme.springSmooth

private val SuhuScreen.ordinalLevel: Int
    get() = when (this) {
        is SuhuScreen.Onboarding -> 0
        is SuhuScreen.Dashboard -> 1
        is SuhuScreen.ManualEntry -> 2
    }

/**
 * Pengendali Navigasi Utama Aplikasi Suhu.
 * Menggunakan state-based navigation tanpa Material Navigation Component, 
 * sesuai arahan arsitektur UI Vibe-Coding.
 */
@Composable
fun SuhuNavHost() {
    var currentScreen by remember { mutableStateOf<SuhuScreen>(SuhuScreen.Onboarding) }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                // Menghitung Hierarki untuk mendeteksi Push (Maju) vs Pop (Mundur)
                val isPush = targetState.ordinalLevel >= initialState.ordinalLevel
                
                val enterOffset = if (isPush) { fullWidth: Int -> fullWidth } else { fullWidth: Int -> -fullWidth }
                val exitOffset = if (isPush) { fullWidth: Int -> -fullWidth } else { fullWidth: Int -> fullWidth }

                // Konfigurasi animasi transisi sesuai Apple Vibe (Spring Smooth)
                (slideInHorizontally(
                    animationSpec = springSmooth(),
                    initialOffsetX = enterOffset
                ) + fadeIn(
                    animationSpec = tween(300)
                )).togetherWith(
                    slideOutHorizontally(
                        animationSpec = springSmooth(),
                        targetOffsetX = exitOffset
                    ) + fadeOut(
                        animationSpec = tween(300)
                    )
                )
            },
            label = "SuhuNavTransition"
        ) { targetScreen ->
            when (targetScreen) {
                is SuhuScreen.Onboarding -> {
                    OnboardingScreen(
                        onActivateScanner = {
                            // Simulasi beralih ke Dashboard setelah onboarding
                            currentScreen = SuhuScreen.Dashboard
                        }
                    )
                }
                is SuhuScreen.Dashboard -> {
                    // Sementara menggunakan DashboardEmptyScreen karena belum
                    // ada logika pengecekan database
                    DashboardEmptyScreen(
                        onAddManualClick = {
                            currentScreen = SuhuScreen.ManualEntry
                        },
                        onAddClick = {
                            currentScreen = SuhuScreen.ManualEntry
                        },
                        onProfileClick = {
                            // Belum ditenutkan ke mana arah profilnya
                        },
                        onNavigate = { route ->
                            // Menghandle bottom bar click (Beranda, Analitik, Profil)
                            // Jika diperlukan kita bisa buat sub-state di dalam sini nanti
                        }
                    )
                }
                is SuhuScreen.ManualEntry -> {
                    ManualEntryFormScreen(
                        onCancel = {
                            currentScreen = SuhuScreen.Dashboard
                        },
                        onSave = { name, price, category, cycle, date ->
                            // TODO: Di fase 5.2 data ini akan dikirim ke ViewModel/Room
                            currentScreen = SuhuScreen.Dashboard
                        }
                    )
                }
            }
        }
    }
}
