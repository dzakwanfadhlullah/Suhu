package com.suhu.app.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

/**
 * Komponen kartu Subscription untuk digunakan dalam horizontal carousel.
 * Telah menggunakan iOS-style shadow dan base bentuk Squircle.
 */
@Composable
fun SubscriptionCard(
    serviceName: String,
    priceLabel: String,
    dateLabel: String,
    iconSymbol: String = "🎵",
    isAlert: Boolean = false,
    onClick: () -> Unit = {}
) {
    // Efek khusus Alert (Border & Dot yang berdenyut/pulsa lembut)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    // Pengaturan Modifikasi Dasar
    var cardModifier = Modifier
        .width(160.dp) // Ukuran lebar tetap yang ideal untuk formasi Carousel
        .shadow(
            elevation = 16.dp, 
            shape = SuhuTheme.shapes.squircle,
            spotColor = SuhuTheme.colors.onSurface.copy(alpha = 0.08f), // Soft shadow ala Apple
            ambientColor = SuhuTheme.colors.onSurface.copy(alpha = 0.02f)
        )
        .clip(SuhuTheme.shapes.squircle)
        .background(SuhuTheme.colors.surface)
        .bouncyClickable(onClick = onClick)

    // Override Modifier dengan Border Merah Bedenyut jika rute Alert diaktifkan
    if (isAlert) {
        cardModifier = cardModifier.border(
            width = 1.5.dp,
            color = SuhuTheme.colors.error.copy(alpha = pulseAlpha),
            shape = SuhuTheme.shapes.squircle
        )
    }

    Column(
        modifier = cardModifier.padding(SuhuTheme.spacing.medium),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Baris Atas: Ikon Layanan & Titik Merah (Optional)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Identitas Ikon (Berbentuk rounded-xl / large)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(SuhuTheme.shapes.large)
                    .background(SuhuTheme.colors.surfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                // Menggunakan teks emoji sebagai simulasi ikon layanan (Netflix, Spotify, Apple, dll)
                BasicText(
                    text = iconSymbol,
                    style = SuhuTheme.typography.headlineMedium
                )
            }

            // Dot Animate-Pulse
            if (isAlert) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(SuhuTheme.colors.error.copy(alpha = pulseAlpha))
                )
            }
        }

        Spacer(modifier = Modifier.height(SuhuTheme.spacing.medium))

        // 2. Baris Tengah: Deskripsi Utama
        Column(modifier = Modifier.fillMaxWidth()) {
            BasicText(
                text = serviceName,
                style = SuhuTheme.typography.titleLarge.copy(
                    color = SuhuTheme.colors.onSurface
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            BasicText(
                text = dateLabel,
                style = SuhuTheme.typography.bodySmall.copy(
                    color = if (isAlert) SuhuTheme.colors.error else SuhuTheme.colors.secondary
                ),
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(SuhuTheme.spacing.medium))

        // 3. Baris Bawah: Harga Moneter
        BasicText(
            text = priceLabel,
            style = SuhuTheme.typography.headlineSmall.copy(
                color = SuhuTheme.colors.onSurface
            ),
            maxLines = 1
        )
    }
}
