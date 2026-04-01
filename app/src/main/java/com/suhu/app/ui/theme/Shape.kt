package com.suhu.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ==========================================
// KUMPULAN SHAPE APPLE-VIBE (SQUIRCLE-LIKE)
// Tanpa komponen Material
// ==========================================
@Immutable
data class SuhuShapes(
    val extraSmall: Shape,
    val small: Shape,
    val medium: Shape,
    val large: Shape,
    val extraLarge: Shape,
    
    // Konstanta eksplisit dari HTML/UI-Rules
    val button: Shape,
    val card: Shape,
    val squircle: Shape
)

val defaultShapes = SuhuShapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
    
    // Penamaan khusus sesuai desain UI-rules
    button = RoundedCornerShape(20.dp),
    card = RoundedCornerShape(24.dp),
    squircle = RoundedCornerShape(32.dp)
)

// ==========================================
// KUMPULAN SPACING (JARAK & PADDING TERTATA)
// ==========================================
@Immutable
data class SuhuSpacing(
    val extraSmall: Dp = 4.dp, // xs
    val small: Dp = 8.dp,      // sm
    val medium: Dp = 16.dp,    // md
    val large: Dp = 24.dp,     // lg
    val extraLarge: Dp = 32.dp,// xl
    
    // Opsional untuk margin sangat lebar di Apple UI
    val xxl: Dp = 40.dp,
    val xxxl: Dp = 48.dp
)

val defaultSpacing = SuhuSpacing()

// ==========================================
// COMPOSITION LOCAL
// Provider API untuk diakses pada root secara hierarchical
// Pengganti MaterialTheme.shapes -> SuhuTheme.shapes
// Menyediakan properti tambahan SuhuTheme.spacing
// ==========================================
val LocalSuhuShapes = staticCompositionLocalOf<SuhuShapes> {
    error("SuhuShapes belum disediakan via CompositionLocalProvider. Pastikan menggunakan blok SuhuTheme { } di setContent.")
}

val LocalSuhuSpacing = staticCompositionLocalOf<SuhuSpacing> {
    error("SuhuSpacing belum disediakan via CompositionLocalProvider. Pastikan menggunakan blok SuhuTheme { } di setContent.")
}
