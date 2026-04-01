package com.suhu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme

/**
 * Komponen Insight/Tips dari Maskot Aplikasi "Suhu".
 * Digunakan secara horizontal di dashboard aplikasi untuk memberikan ulasan tip atau peringatan hemat.
 */
@Composable
fun MascotInsightBox(
    insightText: String,
    modifier: Modifier = Modifier
) {
    // Kotak dasar: Rounded-2xl(16dp) dengan latar aksen Primer transparan (30%)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(SuhuTheme.shapes.large) // large merepresentasikan rounded-2xl (16dp)
            .background(SuhuTheme.colors.primaryContainer.copy(alpha = 0.3f))
            .padding(SuhuTheme.spacing.medium)
    ) {
        Row(
            verticalAlignment = Alignment.Top // Teks bisa panjang 2-baris, Maskot tetap nangkring di atas
        ) {
            // Kontainer Avatar Maskot  (Bulat / Circle)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SuhuTheme.colors.surface),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder Mascot (Contoh: Penguin = Suhu Dingin)
                BasicText(
                    text = "🐧",
                    style = SuhuTheme.typography.headlineMedium
                )
            }
            
            Spacer(modifier = Modifier.width(SuhuTheme.spacing.medium))
            
            // Kolom Teks Tips
            BasicText(
                text = insightText,
                style = SuhuTheme.typography.bodyMedium.copy(color = SuhuTheme.colors.onSurface),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp) // Penyelarasan optikal ke garis pandang mata Avatar
            )
        }
    }
}
