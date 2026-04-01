package com.suhu.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable
import com.suhu.app.ui.theme.springBouncy
import com.suhu.app.ui.theme.springSmooth

// ==========================================
// ENUM RUTE NAVIGASI APLIKASI
// ==========================================
enum class SuhuNavRoute {
    HOME, ANALYTICS, PROFILE
}

// ==========================================
// BOTTOM NAVIGATION BAR (Tanpa Material)
// ==========================================
@Composable
fun SuhuBottomNavBar(
    currentRoute: SuhuNavRoute,
    onNavigate: (SuhuNavRoute) -> Unit
) {
    // Kontainer layer kaca bawah
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 32.dp, // Sesuai requirement rounded-t-32dp
                    topEnd = 32.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            // Glassmorphism layer (Alpha 85% dengan blur tipis)
            .background(SuhuTheme.colors.surface.copy(alpha = 0.85f))
            .blur(radius = 0.5.dp)
            // Penanganan layar penuh Edge-to-Edge agar tidak tertutup nav button OS
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = SuhuTheme.spacing.large,
                    vertical = SuhuTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SuhuNavBarItem(
                iconSymbol = "🏠",
                label = "Beranda",
                isActive = currentRoute == SuhuNavRoute.HOME,
                onClick = { onNavigate(SuhuNavRoute.HOME) }
            )
            
            SuhuNavBarItem(
                iconSymbol = "📊",
                label = "Analitik",
                isActive = currentRoute == SuhuNavRoute.ANALYTICS,
                onClick = { onNavigate(SuhuNavRoute.ANALYTICS) }
            )
            
            SuhuNavBarItem(
                iconSymbol = "👤",
                label = "Profil",
                isActive = currentRoute == SuhuNavRoute.PROFILE,
                onClick = { onNavigate(SuhuNavRoute.PROFILE) }
            )
        }
    }
}

// ==========================================
// ITEM TAB NAVIGASI BAWAH
// ==========================================
@Composable
private fun SuhuNavBarItem(
    iconSymbol: String,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    // Transisi Scale: Membesar jadi 1.1x jika aktif
    val scale by animateFloatAsState(
        targetValue = if (isActive) 1.1f else 1.0f,
        animationSpec = springBouncy(),
        label = "TabScaleAnimation"
    )
    
    // Transisi Warna: Biru jika aktif, Abu-abu jika tidak
    val color by animateColorAsState(
        targetValue = if (isActive) SuhuTheme.colors.primary else SuhuTheme.colors.secondary,
        animationSpec = springSmooth(),
        label = "TabColorAnimation"
    )

    Column(
        modifier = Modifier
            .bouncyClickable(onClick = onClick)
            .padding(SuhuTheme.spacing.small),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Render Ikon (Memakai Emoji sementara sebelum vector dimasukkan)
        BasicText(
            text = iconSymbol,
            style = SuhuTheme.typography.headlineSmall.copy(color = color),
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        )
        
        Spacer(modifier = Modifier.height(SuhuTheme.spacing.extraSmall))
        
        // Render Teks Label Tab
        BasicText(
            text = label,
            style = SuhuTheme.typography.labelSmall.copy(color = color),
            // Teks label juga ikut discale agar serasi
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        )
    }
}
