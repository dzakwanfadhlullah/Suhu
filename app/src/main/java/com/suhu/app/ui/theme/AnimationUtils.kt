package com.suhu.app.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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

/**
 * Modifier efek Shimmer untuk Ghost/Skeleton State.
 * Menggeser linear gradient secara berulang (infinite).
 */
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "ShimmerTransition")
    
    val startOffsetX by transition.animateFloat(
        initialValue = -2f * size.width.toFloat(),
        targetValue = 2f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerOffset"
    )

    val colorsList = listOf(
        com.suhu.app.ui.theme.SuhuTheme.colors.surfaceContainerHigh.copy(alpha = 0.5f),
        com.suhu.app.ui.theme.SuhuTheme.colors.surfaceContainerHighest.copy(alpha = 0.8f),
        com.suhu.app.ui.theme.SuhuTheme.colors.surfaceContainerHigh.copy(alpha = 0.5f)
    )

    this
        .background(
            brush = Brush.linearGradient(
                colors = colorsList,
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        )
        .onGloballyPositioned {
            size = it.size
        }
}

/**
 * Modifier animasi maskot (Bernafas/Floating).
 * Memanipulasi translasi vertikal (naik turun) dan rotasi secara halus terus menerus.
 */
fun Modifier.breathingAnimation(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "BreathingTransition")
    
    // Naik turun perlahan
    val translationY by transition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BreathingTranslation"
    )
    
    // Rotasi sedikit (ayunan tubuh)
    val rotation by transition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = FastOutSlowInEasing), // Waktu beda dengan Y agar organik
            repeatMode = RepeatMode.Reverse
        ),
        label = "BreathingRotation"
    )
    
    // Sedikit nafas (scale membesar sedikit)
    val scale by transition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BreathingScale"
    )

    this.graphicsLayer {
        this.translationY = translationY
        this.rotationZ = rotation
        this.scaleX = scale
        this.scaleY = scale
    }
}
