package com.suhu.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ==========================================
// TOKENS: LIGHT THEME COLORS
// Base color logic derived from web mockup Tailwind Config
// ==========================================
val light_primary = Color(0xFF0058bc)
val light_onPrimary = Color(0xFFffffff)
val light_primaryContainer = Color(0xFF0070eb)
val light_onPrimaryContainer = Color(0xFFfefcff)

val light_secondary = Color(0xFF5d5e63)
val light_onSecondary = Color(0xFFffffff)
val light_secondaryContainer = Color(0xFFe0dfe4)
val light_onSecondaryContainer = Color(0xFF626267)

val light_tertiary = Color(0xFF006b27)
val light_onTertiary = Color(0xFFffffff)
val light_tertiaryContainer = Color(0xFF008733)
val light_onTertiaryContainer = Color(0xFFf7fff2)

val light_error = Color(0xFFba1a1a)
val light_errorContainer = Color(0xFFffdad6)
val light_onError = Color(0xFFffffff)
val light_onErrorContainer = Color(0xFF93000a)

val light_background = Color(0xFFf9f9fe)
val light_onBackground = Color(0xFF1a1c1f)
val light_surface = Color(0xFFf9f9fe)
val light_onSurface = Color(0xFF1a1c1f)
val light_surfaceVariant = Color(0xFFe2e2e7)
val light_onSurfaceVariant = Color(0xFF414755)
val light_outline = Color(0xFF717786)
val light_inverseOnSurface = Color(0xFFf0f0f5)
val light_inverseSurface = Color(0xFF2e3034)
val light_inversePrimary = Color(0xFFadc6ff)
val light_surfaceTint = Color(0xFF005bc1)
val light_outlineVariant = Color(0xFFc1c6d7)
val light_scrim = Color(0xFF000000)

// Surface Containers (Crucial for Apple-Vibe hierarchy gradients & cards)
val light_surfaceContainerLowest = Color(0xFFffffff)
val light_surfaceContainerLow = Color(0xFFf3f3f8)
val light_surfaceContainer = Color(0xFFededf2)
val light_surfaceContainerHigh = Color(0xFFe8e8ed)
val light_surfaceContainerHighest = Color(0xFFe2e2e7)

// ==========================================
// TOKENS: DARK THEME COLORS
// Inverted palette specifically tailored for Dark Mode
// adhering to Apple Clean Look UI Rules.
// Note: Background = #000000, Card Surface = #1C1C1E
// ==========================================
val dark_primary = Color(0xFFadc6ff) // usually inverted primary
val dark_onPrimary = Color(0xFF002e69)
val dark_primaryContainer = Color(0xFF004493)
val dark_onPrimaryContainer = Color(0xFFd8e2ff)

val dark_secondary = Color(0xFFc6c6cb)
val dark_onSecondary = Color(0xFF2f3034)
val dark_secondaryContainer = Color(0xFF46464b)
val dark_onSecondaryContainer = Color(0xFFe2e2e7)

val dark_tertiary = Color(0xFF72fe88)
val dark_onTertiary = Color(0xFF003911)
val dark_tertiaryContainer = Color(0xFF00531c)
val dark_onTertiaryContainer = Color(0xFF8effa0)

val dark_error = Color(0xFFffb4ab)
val dark_errorContainer = Color(0xFF93000a)
val dark_onError = Color(0xFF690005)
val dark_onErrorContainer = Color(0xFFffdad6)

val dark_background = Color(0xFF000000) // Rule imposed
val dark_onBackground = Color(0xFFe2e2e7)
val dark_surface = Color(0xFF000000) // Rule imposed
val dark_onSurface = Color(0xFFe2e2e7)
val dark_surfaceVariant = Color(0xFF42474e)
val dark_onSurfaceVariant = Color(0xFFc1c6d7)
val dark_outline = Color(0xFF8b91a0)
val dark_inverseOnSurface = Color(0xFF1a1c1f)
val dark_inverseSurface = Color(0xFFe2e2e7)
val dark_inversePrimary = Color(0xFF0058bc)
val dark_surfaceTint = Color(0xFFadc6ff)
val dark_outlineVariant = Color(0xFF414755)
val dark_scrim = Color(0xFF000000)

// Surface Containers for Dark Mode Card structures
val dark_surfaceContainerLowest = Color(0xFF141416) // Sub layer
val dark_surfaceContainerLow = Color(0xFF1C1C1E) // Rule imposed Card Surface
val dark_surfaceContainer = Color(0xFF232427)
val dark_surfaceContainerHigh = Color(0xFF2A2B2E)
val dark_surfaceContainerHighest = Color(0xFF35373A)

// ==========================================
// COLOR PALETTE STRUCTURE
// Custom Immutable Class (No Material references)
// ==========================================
@Immutable
data class SuhuColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val errorContainer: Color,
    val onError: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val inverseOnSurface: Color,
    val inverseSurface: Color,
    val inversePrimary: Color,
    val surfaceTint: Color,
    val outlineVariant: Color,
    val scrim: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
)

// ==========================================
// PREDEFINED PALETTES
// ==========================================
val LightColorPalette = SuhuColors(
    primary = light_primary,
    onPrimary = light_onPrimary,
    primaryContainer = light_primaryContainer,
    onPrimaryContainer = light_onPrimaryContainer,
    secondary = light_secondary,
    onSecondary = light_onSecondary,
    secondaryContainer = light_secondaryContainer,
    onSecondaryContainer = light_onSecondaryContainer,
    tertiary = light_tertiary,
    onTertiary = light_onTertiary,
    tertiaryContainer = light_tertiaryContainer,
    onTertiaryContainer = light_onTertiaryContainer,
    error = light_error,
    errorContainer = light_errorContainer,
    onError = light_onError,
    onErrorContainer = light_onErrorContainer,
    background = light_background,
    onBackground = light_onBackground,
    surface = light_surface,
    onSurface = light_onSurface,
    surfaceVariant = light_surfaceVariant,
    onSurfaceVariant = light_onSurfaceVariant,
    outline = light_outline,
    inverseOnSurface = light_inverseOnSurface,
    inverseSurface = light_inverseSurface,
    inversePrimary = light_inversePrimary,
    surfaceTint = light_surfaceTint,
    outlineVariant = light_outlineVariant,
    scrim = light_scrim,
    surfaceContainerLowest = light_surfaceContainerLowest,
    surfaceContainerLow = light_surfaceContainerLow,
    surfaceContainer = light_surfaceContainer,
    surfaceContainerHigh = light_surfaceContainerHigh,
    surfaceContainerHighest = light_surfaceContainerHighest,
)

val DarkColorPalette = SuhuColors(
    primary = dark_primary,
    onPrimary = dark_onPrimary,
    primaryContainer = dark_primaryContainer,
    onPrimaryContainer = dark_onPrimaryContainer,
    secondary = dark_secondary,
    onSecondary = dark_onSecondary,
    secondaryContainer = dark_secondaryContainer,
    onSecondaryContainer = dark_onSecondaryContainer,
    tertiary = dark_tertiary,
    onTertiary = dark_onTertiary,
    tertiaryContainer = dark_tertiaryContainer,
    onTertiaryContainer = dark_onTertiaryContainer,
    error = dark_error,
    errorContainer = dark_errorContainer,
    onError = dark_onError,
    onErrorContainer = dark_onErrorContainer,
    background = dark_background,
    onBackground = dark_onBackground,
    surface = dark_surface,
    onSurface = dark_onSurface,
    surfaceVariant = dark_surfaceVariant,
    onSurfaceVariant = dark_onSurfaceVariant,
    outline = dark_outline,
    inverseOnSurface = dark_inverseOnSurface,
    inverseSurface = dark_inverseSurface,
    inversePrimary = dark_inversePrimary,
    surfaceTint = dark_surfaceTint,
    outlineVariant = dark_outlineVariant,
    scrim = dark_scrim,
    surfaceContainerLowest = dark_surfaceContainerLowest,
    surfaceContainerLow = dark_surfaceContainerLow,
    surfaceContainer = dark_surfaceContainer,
    surfaceContainerHigh = dark_surfaceContainerHigh,
    surfaceContainerHighest = dark_surfaceContainerHighest,
)

// ==========================================
// COMPOSITION LOCAL
// Provider API untuk diakses pada root secara hierarchical
// Pengganti MaterialTheme.colorScheme -> SuhuTheme.colors
// ==========================================
val LocalSuhuColors = staticCompositionLocalOf<SuhuColors> {
    error("SuhuColors belum disediakan via CompositionLocalProvider. Pastikan menggunakan block SuhuTheme { } di setContent.")
}
