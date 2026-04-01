package com.suhu.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import com.suhu.app.ui.theme.SuhuTheme
import com.suhu.app.ui.theme.bouncyClickable

/**
 * Header Navigasi bergaya iOS Glass-morphism tanpa Material Design.
 */
@Composable
fun SuhuTopAppBar(
    onAddClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // Layer container dengan windowInsets untuk Status bar transparan (Edge-to-Edge)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Simulasi Glass-morphism (kombinasi alpha background putih/hitam dan opsi blur rendering)
            .background(SuhuTheme.colors.surface.copy(alpha = 0.85f))
            .blur(radius = 0.5.dp) // Efek subtle
            .windowInsetsPadding(WindowInsets.statusBars) 
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = SuhuTheme.spacing.large,
                    vertical = SuhuTheme.spacing.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Konten Kiri: Title Logo
            BasicText(
                text = "Suhu",
                style = SuhuTheme.typography.headlineMedium.copy(
                    color = SuhuTheme.colors.onSurface
                )
            )

            // Konten Kanan: Actions
            Row(
                horizontalArrangement = Arrangement.spacedBy(SuhuTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ikon Tambah (+)
                BasicText(
                    text = "＋", // Unicode bold Plus sign placeholder as vector icon
                    style = SuhuTheme.typography.headlineMedium.copy(
                        color = SuhuTheme.colors.primary
                    ),
                    modifier = Modifier.bouncyClickable { onAddClick() }
                )
                
                // Ikon Profil
                BasicText(
                    text = "☻", // Unicode human smile placeholder as vector icon
                    style = SuhuTheme.typography.headlineMedium.copy(
                        color = SuhuTheme.colors.primary
                    ),
                    modifier = Modifier.bouncyClickable { onProfileClick() }
                )
            }
        }
    }
}
