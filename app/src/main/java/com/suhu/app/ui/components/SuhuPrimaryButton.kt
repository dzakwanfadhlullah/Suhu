package com.suhu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

/**
 * Tombol Utama (Call-to-Action) kustom.
 * Tidak menggunakan Material Button; murni digambar dengan Compose Foundation.
 */
@Composable
fun SuhuPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSymbol: String? = null,
    enabled: Boolean = true
) {
    // Mendefinisikan Gradien linear 135 derajat (mendekati arah top-start ke bottom-end)
    // Warna: #0058bc -> #0070eb
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            SuhuTheme.colors.primary, 
            SuhuTheme.colors.primaryContainer 
        )
    )

    // Background modifier beradaptasi terhadap status tombol
    val backgroundModifier = if (enabled) {
        Modifier.background(gradientBrush)
    } else {
        Modifier.background(SuhuTheme.colors.surfaceVariant)
    }

    // Shadow-lg (12.dp elevation) gaya iOS dengan pancaran bias warna aksen
    val shadowModifier = if (enabled) {
        Modifier.shadow(
            elevation = 12.dp,
            shape = SuhuTheme.shapes.squircle,
            spotColor = SuhuTheme.colors.primary.copy(alpha = 0.35f),
            ambientColor = SuhuTheme.colors.primary.copy(alpha = 0.1f)
        )
    } else {
        Modifier
    }

    // Susunan Kontainer Tombol
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth() // Menyesuaikan lebar kontainer pemanggil
            .height(56.dp)  // Standar tinggi *touch-target* nyaman 
            .then(shadowModifier)
            .clip(SuhuTheme.shapes.squircle)
            .then(backgroundModifier)
            // Integrasi membal/skala pada saat touch down (scaleDownBy 0.95 atau 95%) 
            .bouncyClickable(
                scaleDownBy = 0.95f, 
                onClick = { if (enabled) onClick() }
            )
    ) {
        // Lapisan konten (Ikon & Teks)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = SuhuTheme.spacing.large)
        ) {
            val contentColor = if (enabled) SuhuTheme.colors.onPrimary else SuhuTheme.colors.onSurfaceVariant
            
            // Render Ikon Varian Jika Ada
            if (iconSymbol != null) {
                BasicText(
                    text = iconSymbol,
                    style = SuhuTheme.typography.titleLarge.copy(color = contentColor)
                )
                Spacer(modifier = Modifier.width(SuhuTheme.spacing.small))
            }
            
            // Render Teks Utama
            BasicText(
                text = text,
                style = SuhuTheme.typography.labelLarge.copy(color = contentColor)
            )
        }
    }
}
