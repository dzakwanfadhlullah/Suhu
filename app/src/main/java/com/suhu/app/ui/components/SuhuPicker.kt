package com.suhu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

/**
 * Pilihan form klikable layaknya sel navigasi iOS yang menampilkan detail value di sebelah kanan.
 * Sangat ideal digunakan memicu Bottom Sheet untuk penentuan siklus atau tanggal (Date Picker).
 */
@Composable
fun SuhuPickerItem(
    label: String,
    value: String,
    iconSymbol: String? = null,
    showDivider: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SuhuTheme.colors.surface)
            .bouncyClickable(onClick = onClick) 
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = SuhuTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ikon Opsional
            if (iconSymbol != null) {
                BasicText(
                    text = iconSymbol,
                    style = SuhuTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.width(SuhuTheme.spacing.medium))
            }

            // Keterangan Label Utama
            BasicText(
                text = label,
                style = SuhuTheme.typography.bodyLarge.copy(color = SuhuTheme.colors.onSurface),
                modifier = Modifier.weight(1f)
            )

            // Value saat ini (Diposisikan di kanan dengan warna sekunder)
            BasicText(
                text = value,
                style = SuhuTheme.typography.bodyLarge.copy(color = SuhuTheme.colors.secondary)
            )

            Spacer(modifier = Modifier.width(SuhuTheme.spacing.small))

            // Chevron (Indikator picker/navigasi)
            BasicText(
                text = "⟩",
                style = SuhuTheme.typography.bodyLarge.copy(color = SuhuTheme.colors.outlineVariant)
            )
        }

        // Garis batas (Divider) setipis sehelai rambut
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = if (iconSymbol != null) 48.dp else SuhuTheme.spacing.medium)
                    .height(0.5.dp)
                    .background(SuhuTheme.colors.surfaceVariant)
            )
        }
    }
}
