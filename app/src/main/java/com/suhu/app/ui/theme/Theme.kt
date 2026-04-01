package com.suhu.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * TEMA UTAMA SUHU
 * Pengganti MaterialTheme. Menggabungkan Warna, Tipografi, Bentuk, dan Jarak.
 */
@Composable
fun SuhuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val typography = defaultTypography
    val shapes = defaultShapes
    val spacing = defaultSpacing

    CompositionLocalProvider(
        LocalSuhuColors provides colors,
        LocalSuhuTypography provides typography,
        LocalSuhuShapes provides shapes,
        LocalSuhuSpacing provides spacing,
        content = content
    )
}

/**
 * Objek helper untuk memudahkan memanggil standar dari Composable.
 * Contoh pemanggilan: SuhuTheme.colors.primary, SuhuTheme.typography.bodyLarge
 */
object SuhuTheme {
    val colors: SuhuColors
        @Composable
        get() = LocalSuhuColors.current

    val typography: SuhuTypography
        @Composable
        get() = LocalSuhuTypography.current

    val shapes: SuhuShapes
        @Composable
        get() = LocalSuhuShapes.current

    val spacing: SuhuSpacing
        @Composable
        get() = LocalSuhuSpacing.current
}
