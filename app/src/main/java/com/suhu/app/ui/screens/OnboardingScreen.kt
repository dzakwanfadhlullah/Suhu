package com.suhu.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suhu.app.ui.components.SuhuPrimaryButton
import com.suhu.app.ui.theme.InterFontFamily
import com.suhu.app.ui.theme.SuhuTheme

/**
 * LAYAR ONBOARDING — "Selamat Datang di Suhu"
 *
 * Task-focused screen (tanpa TopAppBar dan BottomNavBar).
 * Memperkenalkan konsep 100% offline, meminta izin Notification Listener.
 *
 * @param onActivateScanner Callback saat tombol CTA "Aktifkan Scanner Notifikasi" diklik.
 */
@Composable
fun OnboardingScreen(
    onActivateScanner: () -> Unit = {}
) {
    val colors = SuhuTheme.colors
    val typography = SuhuTheme.typography
    val spacing = SuhuTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp)
            .padding(top = spacing.extraLarge, bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ==========================================
        // HEADER: "Selamat Datang di Suhu"
        // 2.25rem ≈ 36sp, Bold, tracking tight
        // ==========================================
        BasicText(
            text = "Selamat Datang di Suhu",
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 40.sp,
                letterSpacing = (-0.5).sp,
                color = colors.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        )

        // ==========================================
        // ILUSTRASI: Si Kancil + Background Tonal Shifts
        // 3 lapis rectangle dengan rotasi berbeda untuk kedalaman visual
        // ==========================================
        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            // Layer 1: Tonal shift paling belakang (rotasi +3°)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .rotate(3f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(colors.surfaceContainerLow)
            )

            // Layer 2: Tonal shift tengah (rotasi -2°)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .rotate(-2f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(colors.surfaceContainerHighest.copy(alpha = 0.3f))
            )

            // Layer 3: Canvas utama ilustrasi (tanpa rotasi)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = colors.outline.copy(alpha = 0.1f),
                        ambientColor = colors.outline.copy(alpha = 0.05f)
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .background(colors.surfaceContainerLowest),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Placeholder maskot Si Kancil
                    // Akan diganti dengan gambar asli saat aset tersedia
                    BasicText(
                        text = "🦌",
                        style = TextStyle(fontSize = 72.sp),
                        modifier = Modifier.padding(bottom = spacing.medium)
                    )

                    // Ikon keamanan (shield) dalam background tonal
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(colors.primary.copy(alpha = 0.05f))
                            .padding(spacing.medium),
                        contentAlignment = Alignment.Center
                    ) {
                        BasicText(
                            text = "🛡️",
                            style = TextStyle(fontSize = 40.sp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ==========================================
        // BADGE: "100% Offline & Aman" (chip hijau)
        // Pill shape, warna tertiary
        // ==========================================
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(colors.tertiary.copy(alpha = 0.1f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                BasicText(
                    text = "✓",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = colors.tertiary
                    )
                )
                Spacer(modifier = Modifier.width(6.dp))
                BasicText(
                    text = "100% OFFLINE & AMAN",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                        color = colors.tertiary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(spacing.medium))

        // ==========================================
        // PARAGRAF PENJELASAN APLIKASI
        // ==========================================
        BasicText(
            text = "Suhu membaca notifikasi bank lokal Anda untuk melacak tagihan otomatis. Data Anda aman tersimpan di HP Anda, tidak dikirim ke server.",
            style = typography.bodyLarge.copy(
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ==========================================
        // BENTO GRID: 2 kolom — Security Points
        // "No Server Upload" & "Local Encryption"
        // ==========================================
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium)
        ) {
            // Kartu 1: No Server Upload
            BentoSecurityCard(
                icon = "☁",
                label = "No Server Upload",
                modifier = Modifier.weight(1f)
            )

            // Kartu 2: Local Encryption
            BentoSecurityCard(
                icon = "🔒",
                label = "Local Encryption",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // ==========================================
        // CTA BUTTON: "Aktifkan Scanner Notifikasi"
        // Menggunakan SuhuPrimaryButton yang sudah ada (Fase 3.7)
        // ==========================================
        SuhuPrimaryButton(
            text = "Aktifkan Scanner Notifikasi",
            onClick = onActivateScanner,
            iconSymbol = "→"
        )

        Spacer(modifier = Modifier.height(spacing.large))

        // ==========================================
        // DISCLAIMER IZIN
        // ==========================================
        BasicText(
            text = "Izin akses notifikasi diperlukan untuk sinkronisasi otomatis.",
            style = typography.bodySmall.copy(
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium)
        )
    }
}

// ==========================================
// KOMPONEN INTERNAL: Kartu Bento Security
// Digunakan hanya di OnboardingScreen
// ==========================================
@Composable
private fun BentoSecurityCard(
    icon: String,
    label: String,
    modifier: Modifier = Modifier
) {
    val colors = SuhuTheme.colors
    val spacing = SuhuTheme.spacing

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surfaceContainerLow)
            .padding(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicText(
            text = icon,
            style = TextStyle(
                fontSize = 24.sp,
                color = colors.primary
            ),
            modifier = Modifier.padding(bottom = spacing.small)
        )
        BasicText(
            text = label,
            style = TextStyle(
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = colors.onSurface,
                textAlign = TextAlign.Center
            )
        )
    }
}
