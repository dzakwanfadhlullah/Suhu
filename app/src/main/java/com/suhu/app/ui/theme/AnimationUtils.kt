package com.suhu.app.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

// ==========================================
// PRESET SPRING ANIMASI APPLE-VIBE
// Aturan: Menghindari tween() demi organikitas.
// ==========================================

/**
 * Spring Bouncy: Cocok untuk interaksi tap, tombol, badge, atau pop-up elemen UI.
 */
fun <T> springBouncy() = spring<T>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
)

/**
 * Spring Smooth: Cocok untuk transisi layar navigasi besar, BottomSheet, atau perpindahan menu.
 */
fun <T> springSmooth() = spring<T>(
    dampingRatio = 0.85f, // Sedikit lebih smooth daripada default NoBouncy
    stiffness = Spring.StiffnessLow
)

// ==========================================
// MODIFIER EXTENSION INTERAKSI
// ==========================================

/**
 * Modifier efek klik gaya iOS (bouncy clickable).
 * - Saat disentuh: elemen mengecil (scale down).
 * - Saat dilepas: elemen memantul kembali ke ukuran semula.
 * - Indikator Ripple *dihilangkan* karena itu ciri khas Material Design.
 *
 * @param scaleDownBy Nilai skala pengecilan saat tombol ditahan (default 0.95f / mengecil 5%).
 * @param onClick Aksi standar ketika tombol diklik.
 */
fun Modifier.bouncyClickable(
    scaleDownBy: Float = 0.95f,
    onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) scaleDownBy else 1f,
        animationSpec = springBouncy(),
        label = "BouncyClickScale"
    )
    
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null, // Matikan efek Ripple Android
            onClick = onClick
        )
}
