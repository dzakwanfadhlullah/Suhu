package com.suhu.app.ui.components

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

/**
 * Komponen Banner persisten ala iOS untuk menampilkan Peringatan/Status kritis,
 * khususnya saat Izin Notification Listener dicabut.
 */
@Composable
fun SuhuStatusBanner(
    title: String = "Akses Notifikasi Terputus",
    message: String = "Suhu tidak dapat melacak langganan Anda secara otomatis tanpa izin akses notifikasi.",
    buttonText: String = "Perbaiki",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing
    val typography = SuhuTheme.typography

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.errorContainer)
            .padding(spacing.medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon Peringatan
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.error.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = "⚠️",
                    style = typography.headlineMedium
                )
            }

            // Teks Keterangan
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = spacing.medium)
            ) {
                BasicText(
                    text = title,
                    style = typography.bodyLarge.copy(
                        color = colors.onErrorContainer,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                BasicText(
                    text = message,
                    style = typography.labelSmall.copy(
                        color = colors.onErrorContainer.copy(alpha = 0.8f)
                    )
                )
            }

            // Tombol Aksi (Buka Setting)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(colors.error)
                    .bouncyClickable {
                        // Membuka Halaman Pengaturan Khusus Listener Android
                        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                        context.startActivity(intent)
                    }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = buttonText,
                    style = typography.labelMedium.copy(
                        color = colors.onError,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
